package by.sanko.selectioncommittee.entity;

public enum Exam {
    RUSSIAN(0),
    BELARUSIAN (1),
    MATH(2),
    PHYSICS (3),
    CHEMISTRY(4),
    FOREIGN_LANGUAGE(5),
    WORLD_HISTORY(6),
    BIOLOGY(7),
    GEOGRAPHY(8),
    SOCIAL_SCIENCE(9),
    BELARUS_HISTORY(10);

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
