package ru.malakhov.qiwitest.UI;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.malakhov.qiwitest.Objects.Element;

import static ru.malakhov.qiwitest.UI.AdapterRecycler.TYPE_VIEW_RADIO;
import static ru.malakhov.qiwitest.UI.MainActivity.TAG;

public class ConstructorRecycler {
    private List<Element> mInitialElements; // все элементы из джейсона
    private List<Element> mListParentEl = new ArrayList<Element>(); // лист парентов
    private List<Element> mListDependencyEl = new ArrayList<Element>(); // лист с депенденси
    private List<Element> mListResult = new ArrayList<Element>(); // лист для отрисовки в ресайклере. Parent+Children
    private Map<Element, List<Element>> mMapParentsChildren = new HashMap<>(); // мап c Parents + Dependency + Children, для работы с изменениями позиции в спиннере

    private final static String TE_FIELD = "field";
    private final static String TE_DEPENDENCY = "dependency";

    ConstructorRecycler(List<Element> list){
        mInitialElements = list;
        initialization();
    }

    private void initialization(){
        initDefaultLists();
        if (!mListParentEl.isEmpty()) { // парент лист не пуст, после initDefaultLists
            createListResult();
        } else{
            Log.d(TAG, "initialization: нет парентов");
        }
    }

    private void initDefaultLists(){
        for (Element el : mInitialElements){
            switch (el.getType()){
                case TE_FIELD :
                    mListParentEl.add(el); // все parent
                    break;
                case TE_DEPENDENCY :
                    mListDependencyEl.add(el); // все dependency
                    break;
            }
        }
    }

    private void createListResult(){
        List<Element> parentsDep;
        for (Element parent : mListParentEl){  // берем 1 парент
            parentsDep = new ArrayList<>(); // лист для зависимостей каждого парента
            mListResult.add(parent);

            for (Element dependency : mListDependencyEl){ // берем 1 зависимость
                if (parent.getName().equals(dependency.getCondition().getField())) { // если имя поля парента = имя полю зависимости, то есть эта зависимость нашего парента
                    String typeWidgetParent = parent.getView().getWidget().getType();
                    if (typeWidgetParent.equals(TYPE_VIEW_RADIO)){ // тип виджета = спиннер
                        int pos = 0;
                        setSelectedPosition(parent, pos); // устанавливаем начальную позицию спиннера
                        String value = parent.getView().getWidget().getChoices().get(pos).getValue(); // значение поля
                        String regex = dependency.getCondition().getPredicate().getPattern(); // паттерн
                        if (isValidRegex(value, regex)) {
                            String typeWidgetChild;
                            for (Element child : dependency.getContent().getElements()){ // элементы депенденси
                                typeWidgetChild = child.getView().getWidget().getType();

                                if (typeWidgetChild.equals(TYPE_VIEW_RADIO)){
                                    setSelectedPosition(child, pos); // устанавливаем начальную позицию Child спиннера
                                }
                            }
                            mListResult.addAll(dependency.getContent().getElements()); // добавили все поля зависимостей
                        }
                    } else{
                        // TODO: 12.07.2018 другой тип парент виджета
                    }
                    parentsDep.add(dependency); // добавляем зависимость к нашему паренту
                }
            }
            mMapParentsChildren.put(parent, parentsDep); //  мап c Parents + Dependency + Children
        }
    }

    public void reInitialization(Element element) {
        if (isParent(element)) { // елемент использовавший спиннер является родителем
            mListResult.clear(); // чистим старый результат
            for (Map.Entry<Element, List<Element>> parentElement : mMapParentsChildren.entrySet()) {
                Element parent = parentElement.getKey();
                mListResult.add(parent); // добавили парент
                for (Element dependency : parentElement.getValue()) { // берем 1 депенденси
                    int pos = parent.getView().getWidget().getSelectedPosition(); // получаем выбранную позицию в спиннере
                    String value = parent.getView().getWidget().getChoices().get(pos).getValue(); // значение спиннера
                    String regex = dependency.getCondition().getPredicate().getPattern(); // паттерн
                    if (isValidRegex(value, regex)) {
                        mListResult.addAll(dependency.getContent().getElements());
                    }
                }
            }
        } else {
                Log.d(TAG, "reInit: Child");
                // TODO: 13.07.2018 тут можно обрабатывать зависимости Child элемента основной зависимости
        }
    }

    private void setSelectedPosition(Element element, int pos){
        element.getView().getWidget().setSelectedPosition(pos);
    }

    public List<Element> getListResult() {
        return mListResult;
    }

    private boolean isValidRegex(String value, String regex){
        return value.matches(regex);
    }

    private boolean isParent(Element element) {
            return mListParentEl.contains(element);
    }
}
