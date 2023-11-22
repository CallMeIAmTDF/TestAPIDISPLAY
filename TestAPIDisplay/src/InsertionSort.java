import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class InsertionSort {
    public ArrayList<Person> insertionSort(ArrayList<Person> persons, int size){
        int i, j;
        for(i = 1; i < size; i++){
            j = i;
            while (j > 0 && persons.get(j - 1).getParentId() > persons.get(j).getParentId()){
                Collections.swap(persons, j, j-1);
                j--;
            }
        }
        return persons;
    }
}
