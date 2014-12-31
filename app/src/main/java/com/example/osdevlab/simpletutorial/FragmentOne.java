package com.example.osdevlab.simpletutorial;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by osdevlab on 12/29/14.
 */
public class FragmentOne extends Fragment {

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout with fragment_one.xml
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        //create TextView 'textViewCurrentTime' and link with texView id from fragment_one.xml
        final TextView textViewCurrentTime = (TextView) view.findViewById(R.id.textView);

        //create Button 'button' and link with button id from fragment_one.xml
        Button button = (Button) view.findViewById(R.id.button);

        //returns the Activity the Fragment is currently associated with
        //In Fragment, this step requires to pass context to other class.
        context = getActivity();

        /*define OnClickListener for button here*/
        button.setOnClickListener(new View.OnClickListener() {
            JsonHelperClass jsonHelperClass = new JsonHelperClass(context);

            public void onClick(View v) {
                //get String result and display it on TextView
                textViewCurrentTime.setText(jsonHelperClass.getResult());
            }
        });
        return view;
    }
}
