package rfbujan.backend.task2.common.token.creator;

import java.util.concurrent.CompletableFuture;

import rfbujan.backend.task2.common.model.User;
import rfbujan.backend.task2.common.model.UserToken;

/**
 * Token creator that provides the means needed for generating a {@link UserToken} for a given {@link User}.
 *
 */
public interface TokenCreator
{
    /**
     * Creates a token for a given user
     * <p>
     * 
     * @param user
     * 		{@link User} representing a user for which a token has to be created.
     * @return
     * 		{@link UserToken} representing the token return for a given user.
     */
    CompletableFuture<UserToken> issueTokenAsync(User user);
}
