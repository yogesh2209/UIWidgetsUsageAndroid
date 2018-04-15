package com.example.yogeshkohli.yogesh_kohli_assignment_2;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;


/**
 * Created by yogeshkohli on 2/10/18.
 */

public class MajorSelectionFragment extends ListFragment {

    int majorsArray;
    int gettedPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.major_list_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle position = getActivity().getIntent().getExtras();
        if (position != null) {
            gettedPosition = position.getInt("position");
            switch(gettedPosition)
            {
                case 0:
                    majorsArray = R.array.phd;
                    break;
                case 1:
                    majorsArray = R.array.edd;
                    break;
                case 2:
                    majorsArray = R.array.ma;
                    break;
                case 3:
                    majorsArray = R.array.ms;
                    break;
                case 4:
                    majorsArray = R.array.mfa;
                    break;
                case 5:
                    majorsArray = R.array.pmd;
                    break;
                default:
                    Log.e("ERROR ALERT", "Invalid Degree!! Please try again later");
            }
        }

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),majorsArray,android.R.layout.simple_list_item_1);
        setListAdapter(adapter);

        //NEXT BUTTON ACTION
        Button buttonNext = (Button) getActivity().findViewById(R.id.buttonDoneMajorSelection);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //done major selection Button code here
                doneButtonAction();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                 @Override
                                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                     setTextViewSelected(getResources().getStringArray(majorsArray)[position]);
                                                 }
                                             }
        );
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        TextView tweet = (TextView)(getListAdapter()).getItem(position);
        Toast.makeText(getActivity(), tweet.getText(), Toast.LENGTH_SHORT).show();

    }
    //setting and getting textview
    public void setTextViewSelected (String text) {
        TextView textview = (TextView)getActivity().findViewById(R.id.textViewSelectedMajor);
        textview.setText(text);
    }

    public String getTextViewSelected() {
        TextView textview = (TextView)getActivity().findViewById(R.id.textViewSelectedMajor);
        String selectedText = textview.getText().toString();
        return selectedText;
    }

    //Toast Method
    public void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
    //Done button action
    public void doneButtonAction() {
        //Not selected any - means cancelled pressed and show him toast
        if (getTextViewSelected().matches("Not selected")) {
            showToast("Please select a major!");
        }
        else {
            //Take him to first screen and ask him to donebutton now
            backToHomeScreen();
        }
    }
    //Passing data and getting back main activity without creating new instance
    public void backToHomeScreen() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("selected_advance_degree",getResources().getStringArray(R.array.advance_degrees)[gettedPosition]);
        intent.putExtra("selected_major_degree",getTextViewSelected());
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
