package ru.geekbrains.lesson8.MVP.models;

import ru.geekbrains.lesson8.MVP.presenters.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public class BookingModel implements Model {


    private Collection<Table> tables;

    public Collection<Table> loadTables(){
        if (tables == null){
            tables = new ArrayList<>();

            tables.add(new Table());
            tables.add(new Table());
            tables.add(new Table());
            tables.add(new Table());
            tables.add(new Table());
        }

        return tables;
    }


    /**
     * Бронирование столика
     * @param reservationDate дата бронирования
     * @param tableNo номер столика
     * @param name имя
     * @return номер бронирования
     */
    public int reservationTable(Date reservationDate, int tableNo, String name){
        Optional<Table> table = loadTables().stream().filter(t -> t.getNo() == tableNo).findFirst();
        if (table.isPresent()){
            Reservation reservation = new Reservation(reservationDate, name);
            table.get().getReservations().add(reservation);
            return reservation.getId();
        }

        throw new RuntimeException("Некорректный номер столика.");
    }

    public int changeReservationTable(int oldReservation, Date reservationDate, int tableNo, String name){
        Optional<Table> tableForChange = loadTables().stream().filter(t -> t.getReservations().removeIf(r -> r.getId() == oldReservation)).findFirst();
        if(tableForChange.isPresent()) {
            Optional<Table> table = loadTables().stream().filter(t -> t.getNo() == tableNo).findFirst();
            if (table.isPresent()){
                Reservation reservation = new Reservation(reservationDate, name);
                table.get().getReservations().add(reservation);
                return reservation.getId();
            }

            throw new RuntimeException("Некорректный номер столика.");
        }

        throw new RuntimeException("Некорректный номер бронирования.");
    }

}
