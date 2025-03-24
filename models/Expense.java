package models;
/*
Explanation:
- when we create an expense, we need to store some information about it.
- Expense is something that we need to make it an object.
- put those information here and use them in your code.
 */

public class Expense{
    int expense;
    int groupId;
    User inDebt;
    User outDebt;

    public Expense(int expense, int groupId, User inDebt, User outDebt){
        this.expense = expense;
        this.groupId = groupId;
        this.inDebt = inDebt;
        this.outDebt = outDebt;
    }

    public int getExpense(){
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public void increaseExpense(int amount){
        this.expense += amount;
    }

    public int getGroupId(){
        return groupId;
    }
    public User getInDebt(){
        return inDebt;
    }
    public void setInDebt(User inDebt){
        this.inDebt = inDebt;
    }
    public User getOutDebt(){
        return outDebt;
    }
    public void setOutDebt(User outDebt){
        this.outDebt = outDebt;
    }

}
