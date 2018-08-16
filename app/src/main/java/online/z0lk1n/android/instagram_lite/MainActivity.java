package online.z0lk1n.android.instagram_lite;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final int MIN_TEXT_LENGTH = 10;
    private TextInputLayout mTextInputLayout;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextInputLayout = findViewById(R.id.textInputLayout);
        mEditText = findViewById(R.id.editText);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (shouldShowError()) {
                    showError();
                } else {
                    hideError();
                }
            }
        });
    }

    private boolean shouldShowError() {
        int textLength = mEditText.getText().length();
        return textLength > 0 && textLength < MIN_TEXT_LENGTH;
    }

    private void showError() {
        mTextInputLayout.setError(getString(R.string.error_input_text)
                + MIN_TEXT_LENGTH);
    }

    private void hideError() {
        mTextInputLayout.setError(null);
    }
}