package main.by.library.util;

import main.by.library.command.Command;
import main.by.library.command.impl.*;

public final class CommandFactory {

    private CommandFactory() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the Command object by the entered string, if there is one or return null
     * @param commandName string command
     * @return a Command object or null
     */
    public static Command defineCommand(String commandName){
        switch (commandName){
            case "loginUser": return new LoginCommand();
            case "logoutUser": return new LogoutCommand();
            case "registerUser": return new RegisterCommand();
            case "showAllBooks": return new ShowAllBooksCommand();
            case "showAllUsers" : return new ShowAllUsersCommand();
            case "showAllOrders": return new ShowAllOrdersCommand();
            case "addNewBook": return new AddNewBookCommand();
            case "addNewGenre": return new AddNewGenreCommand();
            case "addNewAuthor" : return new AddNewAuthorCommand();
            case "addNewSection" :  return new AddNewSectionCommand();
            case "blockUser" : return new BlockUserCommand();
            case "addToCart" : return new AddToCartCommand();
            case "addOrder" : return new AddOrderCommand();
            case "deleteBookFromOrder": return new DelBookFromOrderCommand();
            case "showUserOrder" : return new ShowUserOrderCommand();
            case "findUserOrder" : return new FindUserOrderCommand();
            case "finishOrder" : return new FinishOrderCommand();
            case "editBook" : return new EditBookCommand();
            case "editUser" : return new EditUserCommand();
            case "showUserInfo" : return new ShowUserInfoCommand();
            case "changeLanguage" : return new ChangeLanguageCommand();
            default: return null;
        }
    }
}
