public class Person {
    int personId;
    int familyTreeId;
    int personGender;
    String personName;
    int parentId;
    int fatherId;
    int motherId;
    int nextAdjacentId;
    int previousAdjacentId;

    public Person(int personId, int familyTreeId, int personGender, String personName, int parentId, int fatherId, int motherId, int nextAdjacentId, int previousAdjacentId) {
        this.personId = personId;
        this.familyTreeId = familyTreeId;
        this.personGender = personGender;
        this.personName = personName;
        this.parentId = parentId;
        this.fatherId = fatherId;
        this.motherId = motherId;
        this.nextAdjacentId = nextAdjacentId;
        this.previousAdjacentId = previousAdjacentId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getFamilyTreeId() {
        return familyTreeId;
    }

    public void setFamilyTreeId(int familyTreeId) {
        this.familyTreeId = familyTreeId;
    }

    public int getPersonGender() {
        return personGender;
    }

    public void setPersonGender(int personGender) {
        this.personGender = personGender;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getFatherId() {
        return fatherId;
    }

    public void setFatherId(int fatherId) {
        this.fatherId = fatherId;
    }

    public int getMotherId() {
        return motherId;
    }

    public void setMotherId(int motherId) {
        this.motherId = motherId;
    }

    public int getNextAdjacentId() {
        return nextAdjacentId;
    }

    public void setNextAdjacentId(int nextAdjacentId) {
        this.nextAdjacentId = nextAdjacentId;
    }

    public int getPreviousAdjacentId() {
        return previousAdjacentId;
    }

    public void setPreviousAdjacentId(int previousAdjacentId) {
        this.previousAdjacentId = previousAdjacentId;
    }
}
