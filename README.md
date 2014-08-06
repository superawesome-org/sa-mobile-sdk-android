SuperAwesome Mobile SDK for Android
===================================

Setting Up SAMobileSDK
----------------------
First, you have to add the library to your workspace. To do this, download the library from [here](https://github.com/SuperAwesomeLTD/sa-mobile-sdk-android) then go to Eclipse's 'File' > 'Import' menu, expand the 'Android' section and choose 'Existing Android Code into Workspace'.

After importing the library to your workspace, your application needs to be linked to the SDK library project. View the properties for the project, and navigate to the 'Android' tab. In the lower part of the dialog, click 'Add' and choose the 'SAMobileSDK' project from the workspace.

Next, open the AndroidManifest.xml file in the root of the project. Add a 'Uses Permission' item named android.permission.INTERNET.

Integrating the SuperAwesome Platform
-------------------------------------

###User Authentication
First, open the AndroidManifest.xml file and add a new Activity to the app with the name tv.superawesome.mobile.LoginActivity.

The following code snippet shows how to display the SuperAwesome login view:
```
Button button = (Button) findViewById(R.id.button1);
button.setOnClickListener(new View.OnClickListener() {
  public void onClick(View v) {
    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    startActivityForResult(intent, 101);
  }
});
```
If the authentication succeeds the activity returns with a token, that you can use for further queries. To access this token you have to override the onActivityResult method and get it from the intent:
```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  if (resultCode == RESULT_OK && requestCode == 101) {
	  if (data.hasExtra("token")) {
	    Toast.makeText(this, data.getExtras().getString("token"), Toast.LENGTH_SHORT).show();
	  }
  }
}
```

###Awarding SuperAwesome Points

Integrating SuperAwesome Advertising
------------------------------------
