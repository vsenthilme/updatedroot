class Person{
    String name;
    Person(String name) {this.name =name;}

    public boolean equals(Object ob) {return ((Person)ob).name==this.name;}
//    public int hashCode() {return name.length();}
}