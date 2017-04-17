package kirill.sorokin.ru.calculatorcurrency;

import android.content.Context;

import java.util.Locale;

import kirill.sorokin.ru.calculatorcurrency.xml.XMLParser;
import kirill.sorokin.ru.calculatorcurrency.xml.elements.ValCurs;
import kirill.sorokin.ru.calculatorcurrency.xml.elements.Valute;

/**
 * Created by Kirill on 15.04.2017.
 */
public class CalculatorLogic implements NetworkThread.DownloadCompleteListener {

    public interface CalculatorLogicListener {
        Object loadInstance();

        void setValCurs(ValCurs valCurs);

        void setTextResult(String textResult);

        void showProgressBar(boolean show);
    }

    private NetworkThread networkThread;
    private CalculatorLogicListener listener;
    private Context context;
    private Valute valuteFrom;
    private Valute valuteTo;
    private String text;

    public CalculatorLogic(Context context, CalculatorLogicListener listener) {
        this.listener = listener;
        this.context = context;
    }

    public void updateData() {
        Object obj = listener.loadInstance();
        if (obj != null && obj instanceof NetworkThread) {
            networkThread = (NetworkThread) obj;
        }
        if (networkThread != null && !networkThread.isStop()) {
            Log.d("is already started, connect");
            networkThread.setListener(this);
        } else {
            if (networkThread == null) {
                Log.d("start new task");
                networkThread = new NetworkThread(context);
                networkThread.setListener(this);
                networkThread.execute();
            } else {
                downloadComplete();
            }
        }
    }

    public Object onSaveInstance() {
        return networkThread;
    }

    public void setTextEdit(String text) {
        this.text = text;
    }

    public void setSelectionFrom(Valute valute) {
        valuteFrom = valute;
    }

    public void setSelectionTo(Valute valute) {
        valuteTo = valute;
    }

    public void onClickButton() {
        if (valuteTo != null && valuteFrom != null && text != null && !text.isEmpty()) {
            try {
                long inputValute = Long.valueOf(text);
                float valueFrom = valuteFrom.getValue();
                float valueTo = valuteTo.getValue();
                float result = inputValute * valueFrom / valueTo;
                String resultText = context.getString(R.string.value_result,
                        String.format(Locale.getDefault(), "%.2f", result),
                        valuteTo.getCharCode());
                listener.setTextResult(resultText);
            } catch (Exception e) {
                Log.e(e.getMessage(), e);
            }
        }
    }

    @Override
    public void downloadComplete() {
        listener.showProgressBar(false);
        String text = PrefManager.getValCurs(context);
        if (text != null && !text.isEmpty()) {
            try {
                XMLParser xmlParser = new XMLParser(context, text);
                ValCurs valCurs = xmlParser.parse();
                listener.setValCurs(valCurs);
            } catch (Exception e) {
                Log.e(e.getMessage(), e);
            }
        }
    }
}
