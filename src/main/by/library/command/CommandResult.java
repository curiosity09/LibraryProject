package main.by.library.command;

public class CommandResult {

    public static final String FORWARD = "forward";
    public static final String REDIRECT = "redirect";
    public static final String INCLUDE = "include";

    private String pageName;
    private final String navigationType;

    public CommandResult(String pageName, String navigationType) {
        this.pageName = pageName;
        this.navigationType = navigationType;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getNavigationType() {
        return navigationType;
    }
}
