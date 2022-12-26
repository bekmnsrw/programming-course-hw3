package com.example.hw3.repositories;

import com.example.hw3.jdbc.PostgresConnectionProvider;
import com.example.hw3.models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.nonNull;

public class PatientRepository implements EntityRepository<Patient> {
    private static final String SQL_SELECT_ALL_PATIENTS = "select * from patient";
    private static final String SQL_SELECT_PATIENT_BY_ID = "select * from patient where id = ?";
    private static final String SQL_INSERT_PATIENT = "insert into patient(first_name, second_name, phone_number) values(?, ?, ?)";
    private static final String SQL_UPDATE_PATIENT = "update patient set first_name = ?, second_name = ?, phone_number = ? where id = ?";
    private static final String SQL_DELETE_PATIENT_BY_ID = "delete from patient where id = ?";
    private static final String SQL_FILTER_ALL_BY_FIRST_NAME = "select * from patient where first_name = ?";
    private static final String SQL_FILTER_ALL_BY_SECOND_NAME = "select * from patient where second_name = ?";
    private static final String SQL_FILTER_ALL_BY_PHONE_NUMBER = "select * from patient where phone_number = ?";

    private static final Function<ResultSet, Patient> patientMapper = row -> {
        try {
            return Patient.builder()
                    .id(row.getLong("id"))
                    .firstName(row.getString("first_name"))
                    .secondName(row.getString("second_name"))
                    .phoneNumber(row.getString("phone_number"))
                    .build();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_PATIENTS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    patients.add(patientMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return patients;
    }

    @Override
    public Optional<Patient> findById(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PATIENT_BY_ID)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Patient patient = patientMapper.apply(resultSet);
                    return Optional.of(patient);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void insert(Patient patient) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_PATIENT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getSecondName());
            preparedStatement.setString(3, patient.getPhoneNumber());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't save patient");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                patient.setId(generatedKeys.getLong("id"));
            } else {
                throw new SQLException("Can't obtain generated key");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(Patient patient) {
        Patient patient1 = findById(patient.getId()).orElse(null);
        if (nonNull(patient1)) {
            try (Connection connection = PostgresConnectionProvider.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PATIENT)) {
                try {
                    preparedStatement.setString(1, patient.getFirstName());
                    preparedStatement.setString(2, patient.getSecondName());
                    preparedStatement.setString(3, patient.getPhoneNumber());
                    preparedStatement.setLong(4, patient1.getId());

                    preparedStatement.execute();
                } catch (SQLException e) {
                    throw  new IllegalArgumentException(e);
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            insert(patient);
        }
    }

    @Override
    public void delete(Long id) {
        if (findById(id).isPresent()) {
            try (Connection connection = PostgresConnectionProvider.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_PATIENT_BY_ID)) {
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
    public List<Patient> filterAllByFirstName(String s) {
        List<Patient> patients = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FILTER_ALL_BY_FIRST_NAME)) {
            preparedStatement.setString(1, s);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    patients.add(patientMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return patients;
    }

    @Override
    public List<Patient> filterAllBySecondName(String s) {
        List<Patient> patients = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FILTER_ALL_BY_SECOND_NAME)) {
            preparedStatement.setString(1, s);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    patients.add(patientMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return patients;
    }

    public List<Patient> filterAllByPhoneNumber(String s) {
        List<Patient> patients = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FILTER_ALL_BY_PHONE_NUMBER)) {
            preparedStatement.setString(1, s);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    patients.add(patientMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return patients;
    }
}
