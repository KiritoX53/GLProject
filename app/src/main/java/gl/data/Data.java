package gl.data;

import java.util.ArrayList;

public class Data {
    private ArrayList<Goal> goals;
    private int balance;
    private ArrayList<Transaction> transactions;
    private ArrayList<Budget> budgets;
    public Data(){
        initializeDataStructures();
    }

    private void initializeDataStructures() {
        goals = new ArrayList<>();
        transactions = new ArrayList<>();
        budgets = new ArrayList<>();
        budgets.add(new Budget("General", 0.0f, "General budget while you plan your finances."));
        balance = 0;
    }


    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public void setGoals(ArrayList<Goal> goals) {
        this.goals = goals;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(ArrayList<Budget> budgets) {
        this.budgets = budgets;
    }

}