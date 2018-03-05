package rfbujan.backend.task2.token_provider;

import java.util.concurrent.CompletableFuture;

import rfbujan.backend.task2.common.authentication.TokenAuthenticator;
import rfbujan.backend.task2.common.model.Credentials;
import rfbujan.backend.task2.common.model.UserToken;
import rfbujan.backend.task2.common.token.creator.TokenCreator;
import rfbujan.backend.task2.common.token.provider.TokenProvider;

/**
 * Implementation of the {@link TokenProvider} in response to Task2 of the backend-test exercise.
 * <p>
 * This implementation of the token request is performed in two steps:
 * <ul>
 * <li>1.- credentials authentification.
 * <li>2.- user token creation.
 * </ul>
 * <p>
 * This implementation delegates on two objects for performing the aforementioned actions: {@link TokenAuthenticator}
 * and {@link TokenCreator}.
 * 
 * The thread safety of this class relays on the thread safety implementation of the {@link TokenCreator} and
 * {@link TokenAuthenticator}
 */
public class SimpleAsyncTokenService implements TokenProvider
{

    private final TokenCreator tokenCreator;
    private final TokenAuthenticator tokenAuthenticator;

    public SimpleAsyncTokenService(TokenCreator tokenCreator, TokenAuthenticator tokenAuthentification)
    {
	super();
	this.tokenCreator = tokenCreator;
	this.tokenAuthenticator = tokenAuthentification;
    }

    /**
     * {@inheritDoc}
     * 
     * In this implementation the token request is composed by two asynchronous operations. Credentials are authorized
     * first by the {@link #tokenAuthenticator} which returns a {@link User}. Then a {@link UserToken} is generated by
     * the {@link #tokenCreator} using the generated {@link User} in the previous step.
     * <p>
     * In case of an exception at any point of the request an invalid user token is returned.
     * <p>
     * <b>NOTICE:</b> The non-blocking aspect of this method is actually provided by the implementation of the
     * {@link TokenAuthenticator#authenticateAsync(Credentials)}.
     * <p>
     */
    @Override
    public CompletableFuture<UserToken> requestTokenAsync(Credentials credentials)
    {

	return tokenAuthenticator.authenticateAsync(credentials)
		.thenCompose((user) -> tokenCreator.issueTokenAsync(user)).exceptionally(ex -> handleExceptions());
    }

    private UserToken handleExceptions()
    {
	return UserToken.invalidUserToken();
    }

}