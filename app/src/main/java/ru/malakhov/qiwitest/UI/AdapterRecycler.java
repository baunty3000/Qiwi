package ru.malakhov.qiwitest.UI;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.List;

import ru.malakhov.qiwitest.Objects.Choice;
import ru.malakhov.qiwitest.Objects.Element;
import ru.malakhov.qiwitest.R;

import static ru.malakhov.qiwitest.UI.MainActivity.TAG;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ItemAdapterVH> {

    private List<Element> mInitialElements;

    private final static int TYPE_RADIO = 0;
    private final static int TYPE_TEXT = 1;

    public final static String TYPE_VIEW_RADIO = "radio";
    public final static String TYPE_VIEW_TEXT = "text";

    public final static String TYPE_INPUT_NUMERIC = "numeric";
    public final static String TYPE_INPUT_TEXT = "text";

    private SpinnerCallBack mSpinnerCallBack;
    private Context mContext;

    public AdapterRecycler(Context context) {
        mContext = context;
    }

    public void setSpinnerCallBack(SpinnerCallBack spinnerCallBack) {
        mSpinnerCallBack = spinnerCallBack; }

    public void setElements(List<Element> elements) {
        mInitialElements = elements;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        int result = 0;
        Element element = mInitialElements.get(position);
        switch (element.getView().getWidget().getType()){
            case TYPE_VIEW_RADIO :
                result = TYPE_RADIO;
                break;
            case TYPE_VIEW_TEXT:
                result = TYPE_TEXT;
                break;
        }
        return result;
    }

    @NonNull
    @Override
    public ItemAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutRes = 0;
        switch (viewType){
            case TYPE_RADIO :
                layoutRes = R.layout.spinner;
                break;
            case TYPE_TEXT :
                layoutRes = R.layout.edittext;
                break;
        }
        return new ItemAdapterVH(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemAdapterVH holder, int position) {
        final Element element = mInitialElements.get(position);
        switch (element.getView().getWidget().getType()){
            case TYPE_VIEW_RADIO :
                holder.mTextTitleSpinner.setText(element.getView().getTitle()); // задаем спиннеру title

                String choices[] = getChoicesMas(element.getView().getWidget().getChoices()); // массив элементов спиннера

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, choices);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.mSpinner.setAdapter(adapter);

                final int pos = element.getView().getWidget().getSelectedPosition(); // получили позицию спиннера
                holder.mSpinner.setSelection(pos);

                holder.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position,
                            long id) {
                            if (position != pos) { // пропускаем обработчик сохраненной позиции элемента
                                element.getView().getWidget().setSelectedPosition(position);
                                closeKeyboard(view);
                                mSpinnerCallBack.spinnerItemSelected(element);
                            }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            case TYPE_VIEW_TEXT:
                holder.editText.setText(""); // обнуляем, т.к. ресайкл может использовать текущие вью
                holder.metBottomMessage.setText("");
                if (element.getView().getWidget().getText() != null){
                    holder.editText.setText(element.getView().getWidget().getText());
                } else {
                    holder.editText.setHint(element.getView().getPrompt());
                }
                String key = element.getView().getWidget().getKeyboard();
                if (key != null){
                    holder.editText.setInputType(getKeyboardET(key));
                } else{
                    holder.editText.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                holder.mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String regex = element.getValidator().getPredicate().getPattern();
                        if (isValidField(holder.editText, regex)){
                            holder.metBottomMessage.setTextColor(Color.GREEN);
                            holder.metBottomMessage.setText("Данные корректны");
                            holder.editText.clearFocus();
                            closeKeyboard(v);
                        }
                        else{
                            holder.metBottomMessage.setTextColor(Color.RED);
                            holder.metBottomMessage.setText("Введите корректные данные");
                        }
                    }
                });
                
                holder.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus){
                            Log.d(TAG, "onFocusChange: ");
                            saveEditText(element, holder.editText);
                        }
                    }
                });
                break;
        }
    }

    private void saveEditText(Element element, EditText editText) {
        element.getView().getWidget().setText(editText.getText().toString());
    }

    private void closeKeyboard(View metBottomMessage) {
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(metBottomMessage.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            Log.d(TAG, "closeKeyboard: "+e);
        }
    }

    private String[] getChoicesMas(List<Choice> choices) {
        String choicesMas[] = new String[choices.size()];
        for (int i = 0; i < choices.size(); i++) { // формируем массив элементов спиннера
            choicesMas[i] = choices.get(i).getTitle();
        }
        return choicesMas;
    }

    private int getKeyboardET(String keyboard){
        switch (keyboard){
            case TYPE_INPUT_NUMERIC : return InputType.TYPE_CLASS_NUMBER;
            default: return InputType.TYPE_CLASS_TEXT;
        }
    }

    private boolean isValidField(EditText editText, String regex){
        if (!editText.getText().equals("")&&editText.getText().toString().matches(regex)){
           return true;
        }
        else return false;
    }

    @Override
    public int getItemCount() {
        return mInitialElements.size();
    }

    public class ItemAdapterVH extends RecyclerView.ViewHolder {
        private TextView mTextTitleSpinner;
        private Spinner mSpinner;
        private TextView mtvSpinnerBottom;

        private EditText editText;
        private TextView metBottomMessage;
        private Button mButton;

        public ItemAdapterVH(View itemView) {
            super(itemView);
            mTextTitleSpinner = (TextView) itemView.findViewById(R.id.tvTitle);
            mSpinner = (Spinner) itemView.findViewById(R.id.spinner);
            mtvSpinnerBottom = (TextView) itemView.findViewById(R.id.tvSpinnerBottom);

            metBottomMessage = (TextView) itemView.findViewById(R.id.etBottomMessage);
            editText = (EditText) itemView.findViewById(R.id.editText);
            mButton = (Button) itemView.findViewById(R.id.button);
        }
    }
}
