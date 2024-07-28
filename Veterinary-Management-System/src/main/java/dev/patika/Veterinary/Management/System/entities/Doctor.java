    package dev.patika.Veterinary.Management.System.entities;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.List;

    @Entity
    @Table(name = "doctors")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Doctor {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "doctor_id" )
        private int doctorId;

        private String name;
        private String phone;
        private String mail;
        private String address;
        private String city;

        @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
        private List<AvailableDate> availableDates;

        @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
        private List<Appointment> appointments;


    }