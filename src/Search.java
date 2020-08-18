/**
 * This User class only has the username field in this example.
 * <p>
 * However, in the real project, this User class can contain many more things,
 * for example, the user's shopping cart items.
 */
public class Search {

    private final String title;
    private final String year;
    private final String dev;
    private final String character;

    public Search(String title,String year, String dev, String character) {
        this.title = title;
        this.year = year;
        this.dev = dev;
        this.character = character;
    }

    public String getTitle() {
        return this.title;
    }
    public String getYear() {
        return this.year;
    }
    public String getDev() {
        return this.dev;
    }
    public String getCharacter() {
        return this.character;
    }

}
