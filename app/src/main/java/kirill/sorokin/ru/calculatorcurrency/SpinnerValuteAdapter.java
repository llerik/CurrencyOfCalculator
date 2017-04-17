package kirill.sorokin.ru.calculatorcurrency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import kirill.sorokin.ru.calculatorcurrency.xml.elements.ValCurs;
import kirill.sorokin.ru.calculatorcurrency.xml.elements.Valute;

/**
 * Created by kirill on 13.04.17.
 */
public class SpinnerValuteAdapter extends BaseAdapter {
    private Context context;
    private ValCurs valCurs = null;

    public SpinnerValuteAdapter(Context context) {
        this.context = context;
    }

    public void setValCurs(ValCurs valCurs) {
        this.valCurs = valCurs;
    }

    @Override
    public int getCount() {
        if (valCurs != null) {
            return valCurs.getList().size();
        }
        return 0;
    }

    @Override
    public String getItem(int i) {
        if (valCurs != null) {
            Valute valute = valCurs.getList().get(i);
            return context.getString(R.string.valute_name, valute.getCharCode(), valute.getName());
        }
        return "empty";
    }

    public ValCurs getValCurs() {
        return valCurs;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        if (view == null || !view.getTag().toString().equals("DROPDOWN")) {
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            view.setTag("DROPDOWN");
        }

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getItem(position));
        return view;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null || !view.getTag().toString().equals("NON_DROPDOWN")) {
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false);
            view.setTag("NON_DROPDOWN");
        }
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getItem(position));
        return view;
    }
}
