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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Toast;

import static com.example.yogeshkohli.yogesh_kohli_assignment_2.AdvanceDegreeActivity.*;

/**
 * Created by yogeshkohli on 2/10/18.
 */

public class AdvanceDegreeFragment extends ListFragment {

    Integer getPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.advance_degree_list_fragment,container, false);
    return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.advance_degrees,android.R.layout.simple_list_item_1);
        setListAdapter(adapter);

        //CANCEL BUTTON ACTION
        Button buttonCancel = (Button) getActivity().findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //Cancel Button code here
                setTextViewSelected("Not selected");
            }
        });

        //NEXT BUTTON ACTION
        Button buttonNext = (Button) getActivity().findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //Next Button code here
                nextButtonAction();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                 @Override
                                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                     setTextViewSelected(getResources().getStringArray(R.array.advance_degrees)[position]);
                                                     getPosition = position;
                                                 }
                                             }
        );
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        TextView tweet = (TextView)(getListAdapter()).getItem(position);
        Toast.makeText(getActivity(), tweet.getText(), Toast.LENGTH_SHORT).show();

    }
    //Setting and gettiong textview
    public void setTextViewSelected (String text) {
        TextView textview = (TextView)getActivity().findViewById(R.id.textView);
        textview.setText(text);
    }
    public String getTextViewSelected() {
        TextView textview = (TextView)getActivity().findViewById(R.id.textView);
        String selectedText = textview.getText().toString();
        return selectedText;
    }

    //Toast Method
    public void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
    //Next button action
    public void nextButtonAction() {
        //Not selected any - means cancelled pressed and show him toast
        if (getTextViewSelected().matches("Not selected")) {
            showToast("Please select an advance degree!");
        }
        else {
            //Take him to next screen and ask him to select majors
            fireIntent();
        }
    }
    //Fire Intent and send data to next screen
    public void fireIntent() {
        Intent i = new Intent(getActivity(), MajorSelectionActivity.class);
        i.putExtra("position",getPosition);
        startActivity(i);

    }
}
