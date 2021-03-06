/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jamuz.remote;

/**
 *
 * @author phramusca ( https://github.com/phramusca/JaMuz/ )
 */
public interface ICallBackReception {

	/**
	 *
	 * @param login
	 * @param msg
	 */
	public void received(String login, String msg);
	
	/**
	 *
	 * @param client
	 */
	public void disconnected(ClientInfo client);
	
	/**
	 *
	 * @param client
	 */
	public void authenticated(Client client);
}
