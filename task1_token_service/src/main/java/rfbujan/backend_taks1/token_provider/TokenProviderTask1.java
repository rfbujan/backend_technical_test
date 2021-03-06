package rfbujan.backend_taks1.token_provider;

import java.util.concurrent.CompletableFuture;

import rfbujan.backend_taks1.authentication.TokenAuthenticator;
import rfbujan.backend_taks1.authentication.exception.TokenAuthentificationException;
import rfbujan.backend_taks1.model.Credentials;
import rfbujan.backend_taks1.model.User;
import rfbujan.backend_taks1.model.UserToken;
import rfbujan.backend_taks1.token_creation.TokenCreator;
import rfbujan.backend_taks1.token_creation.exception.TokenCreationException;

/**
 * Implementation of the {@link TokenProvider} in response to Task1 of the backend-test exercise.
 * <p>
 * This implementation of the token request is performed in two steps: credentials authentification; and user token
 * creation. The {@link TokenProviderTask1} delegates on two objects for performing the aforementioned actions:
 * {@link TokenAuthenticator} and {@link TokenCreator}.
 * 
 * The thread safety of this class relays on the thread safety implementation of the {@link TokenCreator} and
 * {@link TokenAuthenticator}
 */
public class TokenProviderTask1 implements TokenProvider
{

    /**
     * ID used for creating an invalid object
     */
    public static final String INVALID_TOKEN = "INVALID";
    
    private final TokenCreator tokenCreator;
    private final TokenAuthenticator tokenAuthenticator;

    public TokenProviderTask1(TokenCreator tokenCreator, TokenAuthenticator tokenAuthentification)
    {
	super();
	this.tokenCreator = tokenCreator;
	this.tokenAuthenticator = tokenAuthentification;
    }

    /**
     * {@inheritDoc}
     * 
     * In this implementation the token request is a two-step process. Credentials are authorized first by the
     * {@link #tokenAuthenticator}  which returns a {@link User}. Then a {@link UserToken} is generated by the
     * {@link #tokenCreator} using the generated {@link User} in the previous step.
     * <p>
     * In case of an exception at any point of the request an invalid user token is returned.
     */
    @Override
    public UserToken requestToken(Credentials credentials)
    {
	UserToken token;
	try
	{
	    token = authenticateAndIssueToken(credentials);

	} catch (TokenCreationException | TokenAuthentificationException e)
	{
	    token = createInvalidUserToken();
	}
	return token;
    }

    private UserToken authenticateAndIssueToken(Credentials credentials)
	    throws TokenAuthentificationException, TokenCreationException
    {
	User user = tokenAuthenticator.authenticate(credentials);
	return tokenCreator.issueToken(user);
    }

    protected static UserToken createInvalidUserToken()
    {
	return new UserToken(INVALID_TOKEN);
    }


    /**
     * 
     * non-blocking implementation of the {@link #requestToken(Credentials)} method
     */
    @Override
    public CompletableFuture<UserToken> requestTokenAsync(Credentials credentials)
    {

	return CompletableFuture.supplyAsync(() ->
	{
	    return requestToken(credentials);
	});
    }
}
