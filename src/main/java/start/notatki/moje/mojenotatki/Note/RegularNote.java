package start.notatki.moje.mojenotatki.Note;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RegularNote extends BaseNote {

    public enum Category {
        SHOPPING, MEETING, DIARY, RECIPE;

        public static List<String> getNames() {
            return Arrays.stream(values())
                    .map(s -> s.name().charAt(0) + s.name().substring(1).toLowerCase())
                    .collect(Collectors.toList());
        }
    }

    private Category category;

    public RegularNote(String title, String content, String noteType, Category category) {
        super(title, content, noteType);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = Category.valueOf(category);
    }
}
