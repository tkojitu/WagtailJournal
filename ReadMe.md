# Spec

* The name of this app is WagtailJournal.
* The app is a simple text editor.
* The app has a text area and a New button.
* The app saves a file automatically:
  * When the app is hidden
  * When New button is pressed
* The app saves a file whose name is timestamp.
  * Timestamp format is YYYYMMDDHHMMSS.txt.
* The timestamp is updated when:
  * The app starts with a empty text area.
  * The New button is pushed.
* If New button is pressed:
  * Content of text area is saved.
  * Text area is cleared.
* The app saved a file in Documents/notes/ folder.
* If the text area is empty, the app doesn't save a file.
* The app always opens the latest file.

# Todo

- Layout the screen
  - Text area
  - New button
- Register "New" in the string resource.
- Introduce a container.
- Make a story.
  - Oyaji
  - Kakaa
  - Musuko
  - Musume
- Oyaji knows Kakaa.
- Kakaa knows Musuko.
- Oyaji is a body of the app.
  - MainActivity has a Oyaji.
+ Kakaa saves a file.
+ Musuko manages timestamp.
+ Musume searches the latest file.
+ Musume tells the latest file to Kakaa.
+ Kakaa tells to Musuko to update timestamp.
+ Make callback for hidden app.
  + onPause
  + Save contents of the text area
  + Don't save a file when the text area is empty.
+ Make callback for New button.
  + Save contents of the text area
  + Clear the text area.
- Introduce git.
+ https://developer.android.com/training/data-storage/manage-all-files
  1. Declare the MANAGE_EXTERNAL_STORAGE permission in the manifest.
  2. Use the ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION intent action to direct users to a system
     settings page where they can enable the following option for your app: Allow access to manage
     all files.
  3. To determine whether your app has been granted the MANAGE_EXTERNAL_STORAGE permission, call
     Environment.isExternalStorageManager().
