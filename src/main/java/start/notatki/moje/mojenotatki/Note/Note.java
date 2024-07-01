package start.notatki.moje.mojenotatki.Note;

public class Note extends BaseNote {

    public enum Type {
        SHOPPING, MEETING, DIARY, RECIPE
    }

    private Type type;

    public Note(String title, String content) {
        this(title, content, Type.DIARY);
    }

    public Note(String title, String content, Type type) {
        super(title, content);
        this.type = type;
    }
}
