package rfbujan.backend.task2.common.model;

import net.jcip.annotations.ThreadSafe;

/**
 * Credentials - A tuple of user name and password that are used to authenticate
 * a customer.
 * 
 * This class is immutable and therefore Thread-safe
 *
 */
@ThreadSafe
public class Credentials
{
    private final String username;
    private final String password;
    
    public Credentials(String username, String password)
    {
	super();
	this.username = username;
	this.password = password;
	
    }
    
    
    public String getUsername()
    {
        return username;
    }


    public String getPassword()
    {
        return password;
    }


    @Override
    public int hashCode()
    {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((username == null) ? 0 : username.hashCode());
	return result;
    }

    /**
     * Another object is considered equal to a UserToken object if it is an
     * instance of UserToken and all attributes (i.e. password and name) are
     * equals among each other.
     * <p>
     */
    @Override
    public boolean equals(Object obj)
    {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Credentials other = (Credentials) obj;
	if (password == null)
	{
	    if (other.password != null)
		return false;
	} else if (!password.equals(other.password))
	    return false;
	if (username == null)
	{
	    if (other.username != null)
		return false;
	} else if (!username.equals(other.username))
	    return false;
	return true;
    }

    
}
