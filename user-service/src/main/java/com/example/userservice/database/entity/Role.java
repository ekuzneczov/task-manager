    package com.example.userservice.database.entity;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.util.Collection;

    @Getter
    @Setter
    @NoArgsConstructor
    @Entity
    @Table(name = "roles")
    public class Role {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long Id;

        @Column(nullable = false, length = 20)
        private String name;

        @ManyToMany(mappedBy = "roles")
        private Collection<User> users;

        @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
        @JoinTable(
                name = "roles_authorities",
                joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id")
        )
        private Collection<Authority> authorities;

        public Role(String name) {
            this.name = name;
        }
    }
