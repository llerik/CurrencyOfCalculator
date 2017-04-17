package kirill.sorokin.ru.calculatorcurrency;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import kirill.sorokin.ru.calculatorcurrency.xml.elements.ValCurs;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CalculatorLogic.CalculatorLogicListener {

    private static final String ID_SPINNER_SELECT_FROM = "ID_SPINNER_SELECT_FROM";
    private static final String ID_SPINNER_SELECT_TO = "ID_SPINNER_SELECT_TO";
    private static final String TEXT_RESULT = "TEXT_RESULT";

    private TextView textViewResult;
    private EditText editViewCount;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private RelativeLayout progressBar;

    private SpinnerValuteAdapter adapterSpinnerFrom;
    private SpinnerValuteAdapter adapterSpinnerTo;
    private CalculatorLogic calculatorLogic;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ID_SPINNER_SELECT_FROM, spinnerFrom.getSelectedItemPosition());
        outState.putInt(ID_SPINNER_SELECT_TO, spinnerTo.getSelectedItemPosition());
        outState.putString(TEXT_RESULT, textViewResult.getText().toString());
    }

    private void load(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ID_SPINNER_SELECT_FROM)) {
                spinnerFrom.setSelection(savedInstanceState.getInt(ID_SPINNER_SELECT_FROM));
            }
            if (savedInstanceState.containsKey(ID_SPINNER_SELECT_TO)) {
                spinnerTo.setSelection(savedInstanceState.getInt(ID_SPINNER_SELECT_TO));
            }
            if (savedInstanceState.containsKey(TEXT_RESULT)) {
                textViewResult.setText(savedInstanceState.getString(TEXT_RESULT));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        calculatorLogic = new CalculatorLogic(this, this);
        calculatorLogic.updateData();
        if (savedInstanceState != null) {
            load(savedInstanceState);
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return calculatorLogic.onSaveInstance();
    }

    private void initView() {
        findViewById(R.id.buttonArrow).setOnClickListener(this);
        findViewById(R.id.buttonCalc).setOnClickListener(this);

        progressBar = (RelativeLayout) findViewById(R.id.frameProgressBar);
        spinnerFrom = (Spinner) findViewById(R.id.spinnerFrom);
        spinnerTo = (Spinner) findViewById(R.id.spinnerTo);
        editViewCount = (EditText) findViewById(R.id.editCount);
        editViewCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editViewCount.getText().toString().isEmpty()) {
                    textViewResult.setText(R.string.text_result);
                }
                calculatorLogic.setTextEdit(editViewCount.getText().toString());
            }
        });
        textViewResult = (TextView) findViewById(R.id.result);

        adapterSpinnerFrom = new SpinnerValuteAdapter(this);
        spinnerFrom.setAdapter(adapterSpinnerFrom);
        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (adapterSpinnerFrom.getValCurs() != null) {
                    if (adapterSpinnerFrom.getValCurs().getList().size() > position) {
                        calculatorLogic.setSelectionFrom(adapterSpinnerFrom.getValCurs().getList().get(position));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        adapterSpinnerTo = new SpinnerValuteAdapter(this);
        spinnerTo.setAdapter(adapterSpinnerTo);
        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (adapterSpinnerTo.getValCurs() != null) {
                    if (adapterSpinnerTo.getValCurs().getList().size() > position) {
                        calculatorLogic.setSelectionTo(adapterSpinnerTo.getValCurs().getList().get(position));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonArrow: {
                arrowClick();
                break;
            }
            case R.id.buttonCalc: {
                calculatorLogic.onClickButton();
                break;
            }
        }
    }

    private void arrowClick() {
        int idFrom = spinnerFrom.getSelectedItemPosition();
        int idTo = spinnerTo.getSelectedItemPosition();
        Log.d("idFrom : " + idFrom + " idTo : " + idTo);
        spinnerFrom.setSelection(idTo, true);
        spinnerTo.setSelection(idFrom, true);
    }

    @Override
    public Object loadInstance() {
        return getLastCustomNonConfigurationInstance();
    }

    @Override
    public void setValCurs(ValCurs valCurs) {
        adapterSpinnerTo.setValCurs(valCurs);
        adapterSpinnerFrom.setValCurs(valCurs);
        adapterSpinnerTo.notifyDataSetChanged();
        adapterSpinnerFrom.notifyDataSetChanged();
        if (valCurs.getList().size() > 1) {
            spinnerTo.setSelection(1, true);
        }
    }

    @Override
    public void setTextResult(String textResult) {
        textViewResult.setText(textResult);
    }

    @Override
    public void showProgressBar(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
