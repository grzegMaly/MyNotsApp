package start.notatki.moje.mojenotatki.Note;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Note extends BaseNote {

    public enum Category {
        SHOPPING, MEETING, DIARY, RECIPE;

        public static List<String> getNames() {
            return Arrays.stream(values())
                    .map(s -> s.name().charAt(0) + s.name().substring(1).toLowerCase())
                    .collect(Collectors.toList());
        }
    }

    private Category category;

    public Note(String category) {
        this.category = Category.valueOf(category.toUpperCase());
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = Category.valueOf(category);
    }
}
