package start.notatki.moje.mojenotatki.Note;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RegularNote extends BaseNote {

    public enum Category {
        SHOPPING, MEETING, DIARY, RECIPE;

        public static List<String> getNames() {
            return Arrays.stream(values())
                    .map(BaseNote::convertEnumToString)
                    .collect(Collectors.toList());
        }
    }

    private Category category;

    public RegularNote(String title, String content, String noteType, String category) {
        super(title, content, noteType);
        this.category = convertStringToEnum(Category.class, category);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = Category.valueOf(category);
    }

    @Override
    public String getEnumValueName() {
        return BaseNote.convertEnumToString(category);
    }
}
