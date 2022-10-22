package cinema;

import java.util.*;

public class Cinema {

    private final boolean isCinemaBig;
    private final int bigCinemaBackRowTicketPrice = 8;
    private final int cinemaDefaultTicketPrice = 10;

    private final int nrOfRows;
    private final int nrOfSeatsInRows;

    private final int totalIncome;

    private int[][] seatArrangement;
    private int nrOfBookedTickets;
    private double seatingPercentage;
    private int currentIncome;


    public Cinema(int NrOfRows, int NrOfSeatsInRows)
    {
        final int seatThreshold = 60;

        this.nrOfRows = NrOfRows;
        this.nrOfSeatsInRows = NrOfSeatsInRows;
        this.seatArrangement = new int[NrOfRows][NrOfSeatsInRows];

        for(int i = 0; i < NrOfRows; i++)
            for(int j = 0; j < NrOfSeatsInRows; j++)
                this.seatArrangement[i][j] = 0;

        if(this.nrOfRows * this.nrOfSeatsInRows <= seatThreshold)
            this.isCinemaBig = false;
        else
            this.isCinemaBig = true;

        totalIncome = calcTotalIncome();

    }
    public int calcTotalIncome()
    {
        if(!isCinemaBig)
        {
            return cinemaDefaultTicketPrice * nrOfRows * nrOfSeatsInRows;
        }
        else
        {
            return (int) Math.floor(((double) this.nrOfRows) / 2) * this.cinemaDefaultTicketPrice * this.nrOfSeatsInRows
                    + (int) Math.ceil(((double) this.nrOfRows) / 2) * this.bigCinemaBackRowTicketPrice * this.nrOfSeatsInRows;
        }
    }

    public void showMenu() {
        int state = -1;
        Scanner scanObj = new Scanner(System.in);

        while(state != 0) {

            switch (state) {
                case -1 -> {
                    printInfo();
                    System.out.print("> ");
                    state = scanObj.nextInt();
                }
                case 1 -> {
                    printSeatArrangement();
                    state = -1;
                }
                case 2 -> {
                    System.out.println("");
                    seatCustomer();
                    System.out.println("");
                    state = -1;
                }
                case 3 -> {
                    printStatistics();
                    state = -1;
                }
                case 0 -> System.exit(0);
            }
        }
    }
    public void printSeatArrangement() {
        final char cBooked = 'B';
        final char cEmpty = 'S';

        System.out.println("");
        System.out.println("Cinema:");
        System.out.print(" ");
        for (int i = 0; i < this.nrOfRows + 1; i++) {
            for (int j = 0; j < this.nrOfSeatsInRows; j++) {

                if (i == 0)
                    System.out.printf(" %d", j + 1);
                else
                {

                 if (j == 0)
                    System.out.printf("%d", i);

                if (seatArrangement[i - 1][j] == 1)
                    System.out.printf(" %c", cBooked);
                else
                    System.out.printf(" %c", cEmpty);
                }

            }
                System.out.println();
        }
        System.out.println();
    }
    public void seatCustomer() {
        int bookedRow, bookedCol;
        int ticketPrice;

        while(true) {
            bookedRow = requestBookedRow();
            bookedCol = requestBookedCol();

            if ((bookedRow >= 1 && bookedRow <= this.nrOfRows) &&
                    (bookedCol >= 1 && bookedCol <= this.nrOfSeatsInRows)) {
                if (seatArrangement[bookedRow - 1][bookedCol - 1] == 0)
                    break;
                else
                    System.out.printf("That ticket has already been purchased!\n\n");
            }
            else
                System.out.println("Wrong Input!\n");
        }

        seatArrangement[bookedRow - 1][bookedCol - 1] = 1;
        ticketPrice = calcTicketPrice(bookedRow);
        System.out.println("");
        System.out.printf("Ticket price: $%d\n",ticketPrice);
        calcStatistics(ticketPrice);

        }

    private int requestBookedRow() {
        Scanner scanObj = new Scanner(System.in);
        System.out.println("Enter a row number:");
        System.out.print("> ");
        return scanObj.nextInt();
    }

    private int requestBookedCol() {
        Scanner scanObj = new Scanner(System.in);
        System.out.println("Enter a seat number in that row:");
        System.out.print("> ");
        return scanObj.nextInt();
    }
    public void printInfo() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    public void printStatistics() {
        System.out.println("");
        System.out.printf("Number of purchased tickets: %d\n", this.nrOfBookedTickets);
        System.out.printf("Percentage: %.2f%%\n", this.seatingPercentage);
        System.out.printf("Current income: $%d\n", this.currentIncome);
        System.out.printf("Total income: $%d\n", this.totalIncome);
        System.out.println("");
    }
    private int calcTicketPrice(int bookedRow) {
        if(!isCinemaBig)
        {
            return cinemaDefaultTicketPrice;
        }
        else
        {
            if(bookedRow <= this.nrOfRows/2)
                return cinemaDefaultTicketPrice;
            else
                return bigCinemaBackRowTicketPrice;
        }
    }

    private void calcNrOfBookedTickets() {
        this.nrOfBookedTickets++;
    }
    private void calcSeatingPercentage() {
        this.seatingPercentage =  ((double)(this.nrOfBookedTickets) / (double) (this.nrOfRows * this.nrOfSeatsInRows))
                *100.0;
    }

    private void calcCurrentIncome(int ticketPrice) {
        this.currentIncome += ticketPrice;
    }

    private void calcStatistics(int ticketPrice) {
        calcNrOfBookedTickets();
        calcSeatingPercentage();
        calcCurrentIncome(ticketPrice);
    }


    public static void main(String[] args) {
        Scanner scanObj = new Scanner(System.in);
        int  NrOfRows, NrOfSeatsInRows;

        System.out.println("Enter the number of rows:");
        System.out.print("> ");
        NrOfRows = scanObj.nextInt();

        System.out.println("Enter the number of seats in each row:");
        System.out.print("> ");
        NrOfSeatsInRows = scanObj.nextInt();

        Cinema cine = new Cinema(NrOfRows,NrOfSeatsInRows);
        System.out.println("");
        cine.showMenu();
    }
}