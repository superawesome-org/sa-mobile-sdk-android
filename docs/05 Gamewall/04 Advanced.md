One of the parameters the gamewall object takes is a listener, which should be implemented and added to the gamewall in order to process rewards and catch other events. An example implementation, with a description of the methods, would be as follows:

```
SAGamewall.Listener gamewallListener = new SAGamewall.Listener() {
    @Override
    public void onAdError(String message) {
        // Error trying to retrieve the placement or instantiate the gamewall objects.
    }

    @Override
    public void onAdLoaded(SAAd ad) {
        // Placement has been loaded and is ready to be displayed.
    }

    @Override
    public void onGiveReward(int amount) {
        // 'amount' contains the amount of your virtual currency that should be given to the user, based on your currency's exchange rate.
        // Retrieve currency so far from preferences and add & save new amount rewarded.
        int currency = preferences.getInt("currency", 0);
        currency += amount;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("currency", currency);
        editor.apply();
        textAmount.setText(String.valueOf(currency));
    }

    @Override
    public void onAvailableChange(boolean available) {
        // This indicates whether the gamewall has been updated; if 'available' is true, there are elements to display and a button is shown to the user to take them to the gamewall.
        if (available) {
            // Show button
            gamewallButton.setVisibility(View.VISIBLE);
        } else {
            // Hide button
            gamewallButton.setVisibility(View.INVISIBLE);
        }
    }
};
```

In this example the `gamewallButton' has an `onClick' event that calls `this.gamewall.show();'. This method opens the gamewall, though if desired could be called directly in `onAvailableChange' to show the gamewall instantly.