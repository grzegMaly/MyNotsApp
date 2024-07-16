package start.notatki.moje.mojenotatki.Note;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseNote {

    public enum NoteType {
        REGULAR_NOTE, DEADLINE_NOTE;

        public static List<String> getNames() {
            return EnumSet.allOf(NoteType.class)
                    .stream()
                    .map(BaseNote::convertEnumToString)
                    .collect(Collectors.toList());
        }

        public String getName() {
            return convertEnumToString(this);
        }
    }

    private String title;
    private String content;
    private NoteType noteType;
    private LocalDate createdDate = LocalDate.now();

    public BaseNote(String title, String content, String noteType) {
        this.title = title;
        this.content = content;
        this.noteType = convertStringToEnum(NoteType.class, noteType);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getNoteType() {
        return convertEnumToString(noteType);
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public static <T extends Enum<T>> T convertStringToEnum(Class<T> enumClass, String value) {
        return Enum.valueOf(enumClass, value.replaceAll(" ", "_").toUpperCase());
    }

    public static <T extends Enum<T>> String convertEnumToString(T value) {
        return Arrays.stream(value.name().split("_"))
                .map(s -> s.charAt(0) + s.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    public abstract String getEnumValueName();
}
