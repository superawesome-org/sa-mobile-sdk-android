Configuring the SDK
===================

Once you've integrated the SuperAwesome SDK, you can access all functionality by including the SuperAwesome library:

.. code-block:: java

    import tv.superawesome.*;

There are also a few global SDK parameters you can change according to your needs:

=============  ==============  =======
Parameter      Values          Meaning
=============  ==============  =======
Configuration  | Production *  | Should the SDK get ads from
               | Staging       | the production or test server.
                               | Test placements are all on production.

Test mode      | Enabled       | Should the SDK serve test ads. For test
               | Disabled *    | placements (30471, 30476, etc) must be Enabled.
=============  ==============  =======
 * = denotes default values

You can leave these settings as they are or change them to fit your testing or production needs.
You can specify them in your activity class (MainActivity.java).

.. code-block:: java

    import tv.superawesome.*;

    public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // mandatory setup for the Android SDK
            SuperAwesome.getInstance().setApplicationContext(getApplicationContext());

            SuperAwesome.getInstance().setConfigurationProduction();
            // SuperAwesome.getInstance().setConfigurationStaging();

            SuperAwesome.getInstance().enableTestMode();
            // SuperAwesome.getInstance().disableTestMode();
        }
    }
