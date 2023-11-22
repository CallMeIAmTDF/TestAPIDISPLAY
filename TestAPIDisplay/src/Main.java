import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Calendar;
import java.util.function.Function;

class FamilyTree{
    int familyTreeId;
    String familyTreeName;
    public FamilyTree(int familyTreeId, String familyTreeName) {
        this.familyTreeId = familyTreeId;
        this.familyTreeName = familyTreeName;
    }
}

class personWithSide{
    int personId;
    String side;

    public personWithSide(int personId, String side) {
        this.personId = personId;
        this.side = side;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}

public class Main {
    static boolean haveMethodAttribute(String attribute, Class<Person> clazz){
        Method[] method = clazz.getMethods();
        for(Method m : method){
            if(m.getName().equals(attribute)){
                return true;
            }
        }
        return false;
    }
    public static Comparator<Person> getComparatorByName(String name, Class<Person> clazz) throws NoSuchMethodException {
        String attribute = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        if(!haveMethodAttribute(attribute, clazz)){
            attribute = "getName";
        }
        Method m = clazz.getMethod(attribute);
        Comparator<Person> cm = Comparator.comparing((Function<Person, Comparable>) (Person t) -> {
            try {
                return (Comparable) m.invoke(t);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
        return cm;
    }
    public static int getParentIdByPersonId(int personId, ArrayList<Person> persons){
        for(int i = 0; i < persons.size(); i++){
            if(persons.get(i).getPersonId() == personId){
                return persons.get(i).getParentId();
            }
        }
        return 0;
    }
    public static int getFatherIdByPersonId(int personId, ArrayList<Person> persons){
        for(int i = 0; i < persons.size(); i++){
            if(persons.get(i).getPersonId() == personId){
                return persons.get(i).getFatherId();
            }
        }
        return 0;
    }
    public static int getMotherIdByPersonId(int personId, ArrayList<Person> persons){
        for(int i = 0; i < persons.size(); i++){
            if(persons.get(i).getPersonId() == personId){
                return persons.get(i).getMotherId();
            }
        }
        return 0;
    }
    public static int getGenderByPersonId(int personId, ArrayList<Person> persons){
        for(int i = 0; i < persons.size(); i++){
            if(persons.get(i).getPersonId() == personId){
                return persons.get(i).getPersonGender();
            }
        }
        return -1;
    }
    public static void getTheMainTree(ArrayList<Integer> personIdInTheMainTree, int personId, ArrayList<Person> persons, ArrayList<personWithSide> personWithSides, String side){
        int parentId = getParentIdByPersonId(personId, persons);
        int motherId = getMotherIdByPersonId(personId, persons);
        int fatherId = getFatherIdByPersonId(personId, persons);
        personIdInTheMainTree.add(personId);
        personWithSides.add(new personWithSide(personId, side));
        if(motherId != 0){
            String mo = side + "1";
            getTheMainTree(personIdInTheMainTree, motherId, persons, personWithSides, mo);
        }
        if(fatherId != 0){
            String fa = side + "0";
            getTheMainTree(personIdInTheMainTree, fatherId, persons, personWithSides, fa);
        }
    }
    public static ArrayList<Integer> isSpouse(ArrayList<Spouse> spouses, int personId){
        ArrayList<Integer> res = new ArrayList<Integer>();
        for(int i = 0; i < spouses.size(); i++){
            if(spouses.get(i).getHusbandId() == personId && spouses.get(i).getWifeId() != 0){
                res.add(spouses.get(i).getWifeId());
            }
            if(spouses.get(i).getWifeId() == personId && spouses.get(i).getHusbandId() != 0){
                res.add(spouses.get(i).getHusbandId());
            }
        }
        return res;
    }

    public static void getPerson(ArrayList<Integer> personsWithCenter, ArrayList<Integer> personIdInTheMainTree, ArrayList<Person> persons, ArrayList<Spouse> spouses) {
        Set<Integer> personTemp = new HashSet<>();
        for (int i = 0; i < personIdInTheMainTree.size(); i++) {
            int personId = personIdInTheMainTree.get(i);
            int parentId = getParentIdByPersonId(personId, persons);
            if (parentId == 0) {
                boolean isChildOfCenter = false;
                for (int x : personsWithCenter) {
                    if (getParentIdByPersonId(x, persons) == personId) {
                        isChildOfCenter = true;
                        break;
                    }
                }
                if (!isChildOfCenter) {
                    personsWithCenter.addAll(isSpouse(spouses, personId));
                }
            }
            int gender = getGenderByPersonId(personId, persons);
            for (Person person : persons) {
                if ((gender == 0 && person.getFatherId() == personId) || (gender == 1 && person.getMotherId() == personId)) {
                    int childPersonId = person.getPersonId();
                    personsWithCenter.add(childPersonId);
                    personTemp.add(childPersonId);
                    personsWithCenter.addAll(isSpouse(spouses, childPersonId));
                }
            }
        }
        if (!personTemp.isEmpty()) {
            getPerson(personsWithCenter, new ArrayList<>(personTemp), persons, spouses);
        }
    }

    public static ArrayList<Integer> getSpouseId(ArrayList<Spouse> spouses, int personId){
        ArrayList<Integer> spouseIds = new ArrayList<Integer>();
        for(int i = 0; i < spouses.size(); i++){
            if(personId == spouses.get(i).getHusbandId() || personId == spouses.get(i).getWifeId()){
                spouseIds.add(spouses.get(i).getSpouseId());
            }
        }
        return spouseIds;
    }

    public static APIDisplay getInfor(ArrayList<Integer> personsWithCenter, int personId, ArrayList<Person> persons, ArrayList<Spouse> spouses, ArrayList<APIDisplay> apiDisplay, ArrayList<personWithSide> personWithSides){
        Person person1 = persons.stream().filter(person -> person.getPersonId() == personId).findFirst().orElse(null);
        personWithSide personSide = personWithSides.stream().filter(s -> s.getPersonId() == personId).findFirst().orElse(null);
        String side = "";
        if(personSide != null)
            side = personSide.getSide();
        APIDisplay api;
        ArrayList<Integer> spouseIds = getSpouseId(spouses, personId);
        ArrayList<Integer> personBySpouse = isSpouse(spouses, personId);
        int grId1 = personId;
        int grId2 = personId;
        int count = 0;
        for(int i = 0; i < personBySpouse.size(); i++){
            int person2 = personBySpouse.get(i);
            APIDisplay apiCheck = apiDisplay.stream().filter(apid -> apid.getGroupId() == personId).findFirst().orElse(null);
            if(apiCheck == null){
                for(int j = 0; j < personsWithCenter.size(); j++){
                    if(personBySpouse.get(i) == personsWithCenter.get(j)){
                        grId1 = person2;
                        grId2 = personId;
                        count++;
                        break;
                    }
                }
            }
            else{
                grId2 = apiCheck.getGroupId();
                grId1 = apiCheck.getGroupId();
                count = 199203;
            }
        }
        if(count > 1){
            api =  new APIDisplay(personId, person1.getParentId(), spouseIds, person1.getPersonName(), grId2, side);
        }
        else{
            api =  new APIDisplay(personId, person1.getParentId(), spouseIds, person1.getPersonName(), grId1, side);
        }
        return api;
    }
    public static void main(String[] args) throws NoSuchMethodException {
        ArrayList<Person> persons = new ArrayList<Person>();
        persons.add(new Person(1, 1, 0, "Thái", 1, 2, 3,13, 6, rank));
        persons.add(new Person(2, 1, 0, "Bố Thái", 0, 0, 0, 0, 0));
        persons.add(new Person(3, 1, 1, "Mẹ Thái", 2, 5, 4, 0,0));
        persons.add(new Person(4, 1, 1, "Bà Ngoại Thái", 0,0,0,0,0));
        persons.add(new Person(5, 1, 0,"Ông Ngoại Thái", 14,18,0,0,0));
        persons.add(new Person(6, 1, 1, "Em gái Thái", 1,2,3,1,0 ));
        persons.add(new Person(7, 1, 1, "Con của Em Gái Thái", 4, 0, 6, 0,0));
        persons.add(new Person(8, 1, 0, "Cháu của em gái Thái", 5, 12,7,0,0));
        persons.add(new Person(9, 1, 0, "Con trai Thái",3,1,0,0,0));
        persons.add(new Person(10, 1, 0, "Chau Noi Thai", 11, 9, 0,0,0));
        persons.add(new Person(11, 1, 1, "Chắt của Thái", 12, 10,0,0,0));
        persons.add(new Person(12, 1, 0,"Chồng của 7", 0,0,0,0,0));
        persons.add(new Person(13, 1, 1, "Chị Của Thái", 1, 2,3,0,1));
        persons.add(new Person(14, 1, 0,"Anh Rể Thái", 10,15,0,0,0));
        persons.add(new Person(15, 1,0,"Bố chồng của chị Thái",0,0,0,0,0));
        persons.add(new Person(16, 1, 0, "Con của Chị Thái", 7, 14, 13, 0,0));
        persons.add(new Person(17, 1, 0, "Cháu của Chị Thái", 8, 16, 0,0,0));
        persons.add(new Person(18, 1, 0, "Bố của 5", 0, 0, 0,0,0));
        persons.add(new Person(19,1,1,"Vợ 2 của bố Thái", 0,0,0,0,0));
        persons.add(new Person(20, 1,0,"Chồng 2 của 7", 0, 0,0,0,0));
        persons.add(new Person(21, 1, 1, "Vợ 2 của 12", 0,0,0,0,0));
        persons.add(new Person(22, 1, 1, "Vợ 2 của 20",0,0,0,0,0));
        persons.add(new Person(23, 1, 1, "Vợ của Thái", 19,24,0,0,0));
        persons.add(new Person(24, 1,0,"Bố vợ Thái", 0,0,0,0,0));
        persons.add(new Person(25, 1, 0, "Con 20 -22", 18, 20, 22,0,0));
        persons.add(new Person(26, 1, 0, "Con 12 - 21", 17, 12, 21, 0,0));

        ArrayList<FamilyTree> familyTrees = new ArrayList<FamilyTree>();
        familyTrees.add(new FamilyTree(1, "Cay 1"));
        familyTrees.add(new FamilyTree(2, "Cay 2"));


        ArrayList<Spouse> spouses = new ArrayList<Spouse>();
        spouses.add(new Spouse(1, 2, 3));
        spouses.add(new Spouse(2, 4, 5));
        spouses.add(new Spouse(3, 1, 23));
        spouses.add(new Spouse(4, 0, 6));
        spouses.add(new Spouse(5, 12, 7));
        spouses.add(new Spouse(6, 8, 0));
        spouses.add(new Spouse(7,14 , 13));
        spouses.add(new Spouse(8, 16, 0));
        spouses.add(new Spouse(9, 17, 0));
        spouses.add(new Spouse(10, 15, 0));
        spouses.add(new Spouse(11, 9,0));
        spouses.add(new Spouse(12, 10, 0));
        spouses.add(new Spouse(13, 0, 11));
        spouses.add(new Spouse(14, 18, 0));
        spouses.add(new Spouse(15, 2,19));
        spouses.add(new Spouse(16,20,7));
        spouses.add(new Spouse(17, 12, 21));
        spouses.add(new Spouse(18, 20, 22));
        spouses.add(new Spouse(19, 24,0));
        spouses.add(new Spouse(20, 25,0));
        spouses.add(new Spouse(21, 26,0));
        ArrayList<personWithSide> personWithSides = new ArrayList<personWithSide>();
        ArrayList<Integer> personIdInTheMainTree = new ArrayList<Integer>();


        getTheMainTree(personIdInTheMainTree, 1, persons, personWithSides, "");


        ArrayList<Integer> personsWithCenter = new ArrayList<>(personIdInTheMainTree);
        getPerson(personsWithCenter, personIdInTheMainTree, persons, spouses);

        Set<Integer> set = new LinkedHashSet<>();
        set.addAll(personsWithCenter);
        personsWithCenter.clear();
        personsWithCenter.addAll(set);

        ArrayList<APIDisplay> apiDisplays = new ArrayList<APIDisplay>();
        for(int i = 0; i < personsWithCenter.size(); i++){
            apiDisplays.add(getInfor(personsWithCenter, personsWithCenter.get(i), persons, spouses, apiDisplays, personWithSides));
        }

        for(int i = 0; i < personsWithCenter.size(); i++){
            apiDisplays.get(i).display();
        }


    }
}