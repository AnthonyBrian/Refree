package com.example.anthony.refree;


import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestActivityTests extends ActivityInstrumentationTestCase2<RunGame> {

    public TestActivityTests() {
        super(RunGame.class);
    }

    public void testActivityExists() {
        RunGame activity = getActivity();
        assertNotNull(activity);
    }

    public void testspielstand() {
        RunGame activity = getActivity();

        final EditText mannschaft1EditText =
                (EditText) activity.findViewById(R.id.editText);


        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mannschaft1EditText.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Test");

        getInstrumentation().waitForIdleSync();



        Button pauseButton =
                (Button) activity.findViewById(R.id.btnpause);

        TouchUtils.clickView(this, pauseButton);


        TextView greetMessage = (TextView) activity.findViewById(R.id.mannschaft1);
        String actualText = greetMessage.getText().toString();
        assertEquals("Welche Mannschaft?", actualText);

    }


}
