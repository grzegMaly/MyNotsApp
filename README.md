JavaFX Note-Taking Application

This is a JavaFX application designed for taking and managing notes. The application uses files to store data, ensuring that your notes are saved persistently across sessions.

Features

- Create, Edit, and Delete Notes: Easily create new notes, edit existing ones, and delete notes you no longer need.
- Persistent Storage: Notes are stored in files, allowing you to access them even after closing the application.
- Multithreading: The application leverages multithreading to ensure a smooth user experience. Background tasks such as saving and loading notes are handled in separate threads to keep the UI responsive
- User-Friendly Interface: Built with JavaFX, the application offers a modern and intuitive graphical user interface.
- Search Functionality: Quickly find notes using the integrated search feature.
- Styling: Customizable CSS styles to change the look and feel of the application.
- Directory Selection: Custom dialog for selecting directories.
- Alerts and Notifications: Custom alerts for user notifications.

Multithreading Details

The application employs multithreading to manage various tasks efficiently:

- File Operations: Reading from and writing to files are performed in background threads to avoid blocking the main UI thread.
- UI Responsiveness: Ensures that the user interface remains responsive by offloading time-consuming operations to separate threads.

Classes Overview

Here's a brief overview of the main classes and their responsibilities:

- BaseConfig.java: An abstract base class for configuration settings.
- FilesManager.java: Manages file operations such as reading and writing notes.
- LoadStyles.java: Loads and applies CSS styles to the JavaFX scene.
- BaseNoteRequest.java: An abstract base class for different types of note requests.
- RegularNoteRequest.java: Handles regular note-specific logic.
- DeadlineNoteRequest.java: Handles deadline note-specific logic.
- NoteRequestConverter.java: Converts different types of note requests to and from string representations.
- NoteRequestModel.java: Model representing note request data.
- NoteRequestViewModel.java: ViewModel for note requests.
- MainForm.java: Main form for the JavaFX application.
- NotesList.java: Manages the list of notes.
- StartForm.java: The starting form of the application.
- CustomDirectoryDialog.java: Custom dialog for selecting directories.
- LeftBar.java: Left sidebar of the application.
- MainScene.java: Main scene of the application.
- Page.java: Represents a page in the application.
- BaseNote.java: Abstract base class for notes.
- DeadlineNote.java: Represents a note with a deadline.
- RegularNote.java: Represents a regular note.
- CustomAlert.java: Custom alert dialogs for user notifications.
- DirectoryUtils.java: Utility class for directory operations.
- ExecutorServiceManager.java: Manages executor services for handling threads.
- MyNotesApp.java: The main application class.
- NoteDetailsDialog.java: Dialog for displaying note details.
