package com.example.hw3.repositories;

import com.example.hw3.jdbc.PostgresConnectionProvider;
import com.example.hw3.models.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.nonNull;

public class DoctorRepository implements EntityRepository<Doctor> {
    private static final String SQL_SELECT_ALL_DOCTORS = "select * from doctor";
    private static final String SQL_SELECT_DOCTOR_BY_ID = "select * from doctor where id = ?";
    private static final String SQL_INSERT_DOCTOR = "insert into doctor(first_name, second_name, speciality) values(?, ?, ?)";
    private static final String SQL_UPDATE_DOCTOR = "update doctor set first_name = ?, second_name = ?, speciality = ? where id = ?";
    private static final String SQL_DELETE_DOCTOR_BY_ID = "delete from doctor where id = ?";
    private static final String SQL_FILTER_ALL_BY_FIRST_NAME = "select * from doctor where first_name = ?";
    private static final String SQL_FILTER_ALL_BY_SECOND_NAME = "select * from doctor where second_name = ?";
    private static final String SQL_FILTER_ALL_BY_SPECIALITY = "select * from doctor where speciality = ?";

    private static final Function<ResultSet, Doctor> doctorMapper = row -> {
        try {
            return Doctor.builder()
                    .id(row.getLong("id"))
                    .firstName(row.getString("first_name"))
                    .secondName(row.getString("second_name"))
                    .speciality(row.getString("speciality"))
                    .build();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_DOCTORS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    doctors.add(doctorMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return doctors;
    }

    @Override
    public void insert(Doctor doctor) {
            try (Connection connection = PostgresConnectionProvider.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_DOCTOR, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, doctor.getFirstName());
                preparedStatement.setString(2, doctor.getSecondName());
                preparedStatement.setString(3, doctor.getSpeciality());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows != 1) {
                    throw new SQLException("Can't save doctor");
                }

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    doctor.setId(generatedKeys.getLong("id"));
                } else {
                    throw new SQLException("Can't obtain generated key");
                }

            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_DOCTOR_BY_ID)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Doctor doctor = doctorMapper.apply(resultSet);
                    return Optional.of(doctor);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(Doctor doctor) {
        Doctor doctor1 = findById(doctor.getId()).orElse(null);
        if (nonNull(doctor1)) {
            try (Connection connection = PostgresConnectionProvider.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_DOCTOR)) {
                try {
                    preparedStatement.setString(1, doctor.getFirstName());
                    preparedStatement.setString(2, doctor.getSecondName());
                    preparedStatement.setString(3, doctor.getSpeciality());
                    preparedStatement.setLong(4, doctor.getId());

                    preparedStatement.execute();
                } catch (SQLException e) {
                    throw  new IllegalArgumentException(e);
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            insert(doctor);
        }
    }

    @Override
    public void delete(Long id) {
        if (findById(id).isPresent()) {
            try (Connection connection = PostgresConnectionProvider.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_DOCTOR_BY_ID)) {
                try {
                    preparedStatement.setLong(1, id);
                    preparedStatement.execute();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public List<Doctor> filterAllByFirstName(String s) {
        List<Doctor> doctors = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FILTER_ALL_BY_FIRST_NAME)) {
            preparedStatement.setString(1, s);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    doctors.add(doctorMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return doctors;
    }

    @Override
    public List<Doctor> filterAllBySecondName(String s) {
        List<Doctor> doctors = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FILTER_ALL_BY_SECOND_NAME)) {
            preparedStatement.setString(1, s);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    doctors.add(doctorMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return doctors;
    }

    public List<Doctor> filterAllBySpeciality(String s) {
        List<Doctor> doctors = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FILTER_ALL_BY_SPECIALITY)) {
            preparedStatement.setString(1, s);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    doctors.add(doctorMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return doctors;
    }
}
