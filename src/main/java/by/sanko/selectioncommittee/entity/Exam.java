package by.sanko.selectioncommittee.entity;

public enum Exam {
    RUSSIAN(0),
    MATH(2),
    CHEMISTRY(4),
    PHYSICS (3),
    BELARUSIAN (1),
    WORLD_HISTORY(6),
    BELARUS_HISTORY(10),
    BIOLOGY(7),
    GEOGRAPHY(8),
    SOCIAL_SCIENCE(9),
    FOREIGN_LANGUAGE(5);

    private int index;
    Exam(int index) {
        this.index = index;
    }
    public int getIndex() {
        return index;
    }

    public static Exam findExamByIndex(int index){
        for(Exam ex : Exam.values()){
            if(ex.index == index){
                return ex;
            }
        }
        return RUSSIAN;
    }
}
