package com.example.yogeshkohli.yogesh_kohli_assignment_2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;

import static com.example.yogeshkohli.yogesh_kohli_assignment_2.R.id.editTextFirstName;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getStoredData();
        setFocusOnEditText();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Bundle data = getIntent().getExtras();
        if (data != null) {
            String majorDegree = data.getString("selected_advance_degree") + " " + data.getString("selected_major_degree");
            setMajorSelectedTextView(majorDegree);
        }
    }
    //Convert Button Action
    public void selectMajorButtonClicked(View button) {
       fireIntent();
    }
    //getting stored datafrom shared preferences and showing on UI
    public void getStoredData() {
        SharedPreferences prefs = getSharedPreferences("PersonalData", MODE_PRIVATE);
        String firstName = prefs.getString("firstName", null);
        System.out.println(firstName);
        if (firstName != null) {
            EditText editTextFirstName = (EditText)findViewById(R.id.editTextFirstName);
            editTextFirstName.setText(firstName, TextView.BufferType.EDITABLE);
        }

        String lastName = prefs.getString("lastName", null);
        if (lastName != null) {
            EditText editTextLastName = (EditText)findViewById(R.id.editTextLastName);
            editTextLastName.setText(lastName, TextView.BufferType.EDITABLE);
        }

        String email = prefs.getString("email", null);
        if (email != null) {
            EditText editTextEmail = (EditText)findViewById(R.id.editTextEmail);
            editTextEmail.setText(email, TextView.BufferType.EDITABLE);
        }

        String phone = prefs.getString("phone", null);
        if (phone != null) {
            EditText editTextPhone = (EditText)findViewById(R.id.editTextPhone);
            editTextPhone.setText(phone, TextView.BufferType.EDITABLE);
        }

        String age = prefs.getString("age", null);
        if (age != null) {
            EditText editTextAge = (EditText)findViewById(R.id.editTextAge);
            editTextAge.setText(age, TextView.BufferType.EDITABLE);
        }

        String major_degree_selected = prefs.getString("major_degree_selected", null);
        if (major_degree_selected != null) {
           setMajorSelectedTextView(major_degree_selected);
        }
    }
    //Toast Method
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    //Validations for age, phone, email
    public Boolean isAgeValid(String age){
        if (Integer.parseInt(age) > 0) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isValidPhone(CharSequence phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }
    public boolean isValidEmail(CharSequence email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
    //Setting the focus to first name edit text
    public void setFocusOnEditText() {
        EditText firstNameEditText = (EditText) findViewById(editTextFirstName);
        firstNameEditText.requestFocus();
    }
    //checking validation of all edit text
    public void checkingEditText() {
        if (getFirstNameEditText().length() != 0 && getLastNameEditText().length() != 0 && getAgeEditText().length() != 0 && getPhoneEditText().length() != 0 && getEmailEditText().length() != 0) {
            if (isAgeValid(getAgeEditText()) == true) {
                if(isValidPhone(getPhoneEditText())) {
                    if (isValidEmail(getEmailEditText()) == true) {
                        if (getMajorDegreeTextView().matches("not selected")) {
                            showToast("Please select major");
                        }
                        else{
                            storeDataInSharedPreferences();
                        }

                    }
                    else {
                        showToast("Please enter a valid email!");
                    }
                }
                else {
                    showToast("Please enter a valid phone!");
                }
            }
            else {
                showToast("Please enter a valid age!");
            }
        }
        else{
            showToast("Please enter required fields first!!");
        }
    }
    //Get EditText Values
    public String getFirstNameEditText(){
        EditText firstNameEditText = (EditText) findViewById(editTextFirstName);
        String  firstName = firstNameEditText.getText().toString().trim();
        return firstName;
    }
    public String getLastNameEditText(){
        EditText lastNameEditText = (EditText) findViewById(R.id.editTextLastName);
        String  lastName = lastNameEditText.getText().toString().trim();
        return lastName;
    }
    public String getAgeEditText(){
        EditText ageEditText = (EditText) findViewById(R.id.editTextAge);
        String  age = ageEditText.getText().toString().trim();
        return age;
    }
    public String getEmailEditText(){
        EditText emailEditText = (EditText) findViewById(R.id.editTextEmail);
        String  email = emailEditText.getText().toString().trim();
        return email;
    }
    public String getPhoneEditText(){
        EditText phoneEditText = (EditText) findViewById(R.id.editTextPhone);
        String  phone = phoneEditText.getText().toString().trim();
        return phone;
    }
    public String getMajorDegreeTextView(){
        TextView textView = (TextView)findViewById(R.id.textViewSelectedMajorString);
        String  textViewMajorSelected = textView.getText().toString().trim();
        return textViewMajorSelected;
    }
    //setting textview major selection
    public void  setMajorSelectedTextView(String str) {
        TextView textView = (TextView)findViewById(R.id.textViewSelectedMajorString);
        textView.setText(str);
    }

    //Fire Intent
    public void fireIntent() {
        Intent i = new Intent(this, AdvanceDegreeActivity.class);
        Bundle bundle = new Bundle();
        i.putExtras(bundle);
        startActivity(i);
    }
    //storing data in shared preferences
    public void storeDataInSharedPreferences() {
        Intent myIntent = getIntent();
        SharedPreferences.Editor editor = getSharedPreferences("PersonalData", MODE_PRIVATE).edit();
        editor.putString("firstName",getFirstNameEditText());
        editor.putString("lastName", getLastNameEditText());
        editor.putString("email", getEmailEditText());
        editor.putString("phone", getPhoneEditText());
        editor.putString("age", getAgeEditText());
        editor.putString("major_degree_selected",getMajorDegreeTextView());
        editor.apply();
        showToast("Data Saved!!");
    }
    //Convert Button Action
    public void doneButtonClicked(View button) {
        checkingEditText();
    }
}
