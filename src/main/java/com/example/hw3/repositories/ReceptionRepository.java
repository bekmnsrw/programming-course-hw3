package com.example.hw3.repositories;

import com.example.hw3.jdbc.PostgresConnectionProvider;
import com.example.hw3.models.Reception;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.nonNull;

public class ReceptionRepository {
    private static final String SQL_SELECT_ALL_RECEPTIONS = "select" +
            "    reception.id, " +
            "    reception_date, " +
            "    start_time, " +
            "    end_time, " +
            "    d.second_name as doctor_second_name, " +
            "    p.second_name as patient_second_name, " +
            "    d.id as doctor_id, " +
            "    p.id as patient_id " +
            "from reception" +
            "    join doctor d on reception.doctor_id = d.id " +
            "    join patient p on reception.patient_id = p.id ";

    private static final String SQL_INSERT_RECEPTION = "insert into reception(" +
            "   reception_date, " +
            "   start_time, " +
            "   end_time, " +
            "   doctor_id, " +
            "   patient_id) " +
            "values(?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_RECEPTION_BY_ID = "select " +
            "    reception.id, " +
            "    reception_date, " +
            "    start_time, " +
            "    end_time, " +
            "    d.second_name as doctor_second_name, " +
            "    p.second_name as patient_second_name, " +
            "    d.id as doctor_id, " +
            "    p.id as patient_id " +
            "from reception " +
            "    join doctor d on reception.doctor_id = d.id " +
            "    join patient p on reception.patient_id = p.id " +
            "where reception.id = ?";

    private static final String SQL_DELETE_RECEPTION_BY_ID = "delete from reception " +
            "where reception.id = ?";

    private static final String SQL_UPDATE_RECEPTION = "update reception set " +
            "   reception_date = ?, " +
            "   start_time = ?, " +
            "   end_time = ?, " +
            "   doctor_id = ?, " +
            "   patient_id = ? " +
            "where reception.id = ?";

    private static final String SQL_FILTER_ALL_BY_DATE = "select reception.id, " +
            "       reception_date, " +
            "       start_time, " +
            "       end_time, " +
            "       d.second_name as doctor_second_name, " +
            "       p.second_name as patient_second_name, " +
            "       d.id as doctor_id, " +
            "       p.id as patient_id " +
            "from reception " +
            "    join doctor d on d.id = reception.doctor_id " +
            "    join patient p on p.id = reception.patient_id " +
            "where reception_date = ?";

    private static final String SQL_FILTER_ALL_BY_START_TIME = "select " +
            "       reception.id, " +
            "       reception_date, " +
            "       start_time, " +
            "       end_time, " +
            "       d.second_name as doctor_second_name, " +
            "       p.second_name as patient_second_name, " +
            "       d.id as doctor_id, " +
            "       p.id as patient_id " +
            "from reception " +
            "         join doctor d on d.id = reception.doctor_id " +
            "         join patient p on p.id = reception.patient_id " +
            "where start_time = ?";

    private static final String SQL_FILTER_ALL_BY_END_TIME = "select " +
            "       reception.id, " +
            "       reception_date, " +
            "       start_time, " +
            "       end_time, " +
            "       d.second_name as doctor_second_name, " +
            "       p.second_name as patient_second_name, " +
            "       d.id as doctor_id, " +
            "       p.id as patient_id " +
            "from reception " +
            "    join doctor d on d.id = reception.doctor_id " +
            "    join patient p on p.id = reception.patient_id " +
            "where end_time = ?";

    private static final String SQL_FILTER_ALL_BY_DOCTOR_ID = "select " +
            "       reception.id, " +
            "       reception_date, " +
            "       start_time, " +
            "       end_time, " +
            "       d.second_name as doctor_second_name, " +
            "       p.second_name as patient_second_name, " +
            "       d.id as doctor_id, " +
            "       p.id as patient_id " +
            "from reception " +
            "    join doctor d on d.id = reception.doctor_id " +
            "    join patient p on p.id = reception.patient_id " +
            "where doctor_id = ?";

    private static final String SQL_FILTER_ALL_BY_PATIENT_ID = "select " +
            "       reception.id, " +
            "       reception_date, " +
            "       start_time, " +
            "       end_time, " +
            "       d.second_name as doctor_second_name, " +
            "       p.second_name as patient_second_name, " +
            "       d.id as doctor_id, " +
            "       p.id as patient_id " +
            "from reception " +
            "         join doctor d on d.id = reception.doctor_id " +
            "         join patient p on p.id = reception.patient_id " +
            "where patient_id = ?";

    private static final String SQL_FILTER_ALL_BY_DOCTOR_SECOND_NAME = "select " +
            "       reception.id, " +
            "       reception_date, " +
            "       start_time, " +
            "       end_time, " +
            "       d.second_name as doctor_second_name, " +
            "       p.second_name as patient_second_name, " +
            "       d.id as doctor_id, " +
            "       p.id as patient_id " +
            "from reception " +
            "         join doctor d on d.id = reception.doctor_id " +
            "         join patient p on p.id = reception.patient_id " +
            "where d.second_name = ?";

    private static final String SQL_FILTER_ALL_BY_PATIENT_SECOND_NAME = "select " +
            "       reception.id, " +
            "       reception_date, " +
            "       start_time, " +
            "       end_time, " +
            "       d.second_name as doctor_second_name, " +
            "       p.second_name as patient_second_name, " +
            "       d.id as doctor_id, " +
            "       p.id as patient_id " +
            "from reception " +
            "         join doctor d on d.id = reception.doctor_id " +
            "         join patient p on p.id = reception.patient_id " +
            "where p.second_name = ?";

    private static final Function<ResultSet, Reception> receptionMapper = row -> {
        try {
            return Reception.builder()
                    .id(row.getLong("id"))
                    .date(row.getString("reception_date"))
                    .startTime(row.getString("start_time"))
                    .endTime(row.getString("end_time"))
                    .doctorSecondName(row.getString("doctor_second_name"))
                    .patientSecondName(row.getString("patient_second_name"))
                    .doctorId(row.getLong("doctor_id"))
                    .patientId(row.getLong("patient_id"))
                    .build();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    public List<Reception> findAll() {
        List<Reception> receptions = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_RECEPTIONS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    receptions.add(receptionMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return receptions;
    }

    public void insert(Reception reception) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_RECEPTION, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, reception.getDate());
            preparedStatement.setString(2, reception.getStartTime());
            preparedStatement.setString(3, reception.getEndTime());
            preparedStatement.setLong(4, reception.getDoctorId());
            preparedStatement.setLong(5, reception.getPatientId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't save reception");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                reception.setId(generatedKeys.getLong("id"));
            } else {
                throw new SQLException("Can't obtain generated key");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Optional<Reception> findById(Long id) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_RECEPTION_BY_ID)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Reception reception = receptionMapper.apply(resultSet);
                    return Optional.of(reception);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void delete(Long id) {
        if (findById(id).isPresent()) {
            try (Connection connection = PostgresConnectionProvider.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_RECEPTION_BY_ID)) {
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

    public void update(Reception reception) {
        Reception reception1 = findById(reception.getId()).orElse(null);
        if (nonNull(reception1)) {
            try (Connection connection = PostgresConnectionProvider.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_RECEPTION)) {
                try {
                    preparedStatement.setString(1, reception.getDate());
                    preparedStatement.setString(2, reception.getStartTime());
                    preparedStatement.setString(3, reception.getEndTime());
                    preparedStatement.setLong(4, reception.getDoctorId());
                    preparedStatement.setLong(5, reception.getPatientId());
                    preparedStatement.setLong(6, reception.getId());

                    preparedStatement.execute();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            insert(reception);
        }
    }

    public List<Reception> filterAllByDate(String s) {
        List<Reception> receptions = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FILTER_ALL_BY_DATE)) {
            preparedStatement.setString(1, s);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    receptions.add(receptionMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return receptions;
    }

    public List<Reception> filterAllByStartTime(String s) {
        List<Reception> receptions = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FILTER_ALL_BY_START_TIME)) {
            preparedStatement.setString(1, s);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    receptions.add(receptionMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return receptions;
    }

    public List<Reception> filterAllByEndTime(String s) {
        List<Reception> receptions = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FILTER_ALL_BY_END_TIME)) {
            preparedStatement.setString(1, s);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    receptions.add(receptionMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return receptions;
    }

    public List<Reception> filterAllByDoctorId(Long id) {
        List<Reception> receptions = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FILTER_ALL_BY_DOCTOR_ID)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    receptions.add(receptionMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return receptions;
    }

    public List<Reception> filterAllByPatientId(Long id) {
        List<Reception> receptions = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FILTER_ALL_BY_PATIENT_ID)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    receptions.add(receptionMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return receptions;
    }

    public List<Reception> filterAllByDoctorSecondName(String s) {
        List<Reception> receptions = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FILTER_ALL_BY_DOCTOR_SECOND_NAME)) {
            preparedStatement.setString(1, s);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    receptions.add(receptionMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return receptions;
    }

    public List<Reception> filterAllByPatientSecondName(String s) {
        List<Reception> receptions = new ArrayList<>();

        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FILTER_ALL_BY_PATIENT_SECOND_NAME)) {
            preparedStatement.setString(1, s);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    receptions.add(receptionMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return receptions;
    }
}
