/**
 * 
 */
package rfbujan.backend.task2.athenticator;

import static rfbujan.backend.task2.common.utils.Utils.randomDelay;

import java.util.concurrent.CompletableFuture;

import net.jcip.annotations.ThreadSafe;
import rfbujan.backend.task2.common.authentication.TokenAuthenticator;
import rfbujan.backend.task2.common.model.Credentials;
import rfbujan.backend.task2.common.model.User;

/**
 * {@link TokenAuthenticator} that performs the authentification of given credentials with a random delay between 0 and
 * 5 seconds. This allows to simulate unpredictable delays, caused by everything from sever load to network delays.
 * <p>
 * This implementation is thread-safe since it has no state and the methods has no side effects (referential
 * transparency).
 * <p>
 */
@ThreadSafe
public class TokenAuthenticatorRandomDelay implements TokenAuthenticator
{

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalArgumentException
     *             if the input is <code>null</code> or any of its attributes (i.e. {@link Credentials#getPassword()} or
     *             {@link Credentials#getUsername()})
     * 
     */
    @Override
    public CompletableFuture<User> authenticateAsync(Credentials credentials)
    {
	checkInputStatus(credentials);

	return CompletableFuture.supplyAsync(() ->
	{
	    return authenticate(credentials);
	});
    }

    /*
     * makes sure that the input is not corrupted with null values
     */
    private void checkInputStatus(Credentials credentials)
    {
	if (credentials == null || credentials.getUsername() == null || credentials.getPassword() == null)
	{
	    throw new IllegalArgumentException("Credentials cannot be null");
	}

    }

    private User authenticate(Credentials credentials)
    {
	randomDelay();
	if (usernameUpperCaseEqualsPassword(credentials))
	{
	    return new User(credentials.getUsername());
	} else
	{
	    return User.invalidUser();
	}
    }

    private boolean usernameUpperCaseEqualsPassword(Credentials credentials)
    {
	String username = credentials.getUsername();
	String password = credentials.getPassword();

	return username.toUpperCase().equals(password);
    }

}
