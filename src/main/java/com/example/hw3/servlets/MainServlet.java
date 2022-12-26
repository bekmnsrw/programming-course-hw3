package com.example.hw3.servlets;

import com.example.hw3.models.Doctor;
import com.example.hw3.models.Patient;
import com.example.hw3.models.Reception;
import com.example.hw3.repositories.DoctorRepository;
import com.example.hw3.repositories.PatientRepository;
import com.example.hw3.repositories.ReceptionRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.nonNull;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private ReceptionRepository receptionRepository;

    @Override
    public void init() {
        doctorRepository = new DoctorRepository();
        patientRepository = new PatientRepository();
        receptionRepository = new ReceptionRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String doctorFilter = req.getParameter("doctorFilter");
        String patientFilter = req.getParameter("patientFilter");
        String receptionFilter = req.getParameter("receptionFilter");

        List<Doctor> doctors;
        if (nonNull(doctorFilter)) {
            switch (doctorFilter) {
                case ("filterDoctorFirstName"):
                    doctors = doctorRepository.filterAllByFirstName(req.getParameter("doctorFilterInput"));
                    req.setAttribute("doctors", doctors);
                    break;
                case ("filterDoctorSecondName"):
                    doctors = doctorRepository.filterAllBySecondName(req.getParameter("doctorFilterInput"));
                    req.setAttribute("doctors", doctors);
                    break;
                case ("filterDoctorSpeciality"):
                    doctors = doctorRepository.filterAllBySpeciality(req.getParameter("doctorFilterInput"));
                    req.setAttribute("doctors", doctors);
                    break;
                default:
                    doctors = doctorRepository.findAll();
                    req.setAttribute("doctors", doctors);
                    break;
            }
        } else {
            doctors = doctorRepository.findAll();
            req.setAttribute("doctors", doctors);
        }

        List<Patient> patients;
        if (nonNull(patientFilter)) {
            switch (patientFilter) {
                case ("filterPatientFirstName"):
                    patients = patientRepository.filterAllByFirstName(req.getParameter("patientFilterInput"));
                    req.setAttribute("patients", patients);
                    break;
                case ("filterPatientSecondName"):
                    patients = patientRepository.filterAllBySecondName(req.getParameter("patientFilterInput"));
                    req.setAttribute("patients", patients);
                    break;
                case ("filterPatientPhoneNumber"):
                    patients = patientRepository.filterAllByPhoneNumber(req.getParameter("patientFilterInput"));
                    req.setAttribute("patients", patients);
                    break;
                default:
                    patients = patientRepository.findAll();
                    req.setAttribute("patients", patients);
                    break;
            }
        } else {
            patients = patientRepository.findAll();
            req.setAttribute("patients", patients);
        }

        List<Reception> receptions;
        if (nonNull(receptionFilter)) {
            switch (receptionFilter) {
                case ("filterReceptionDate"):
                    receptions = receptionRepository.filterAllByDate(req.getParameter("receptionFilterInput"));
                    req.setAttribute("receptions", receptions);
                    break;
                case ("filterReceptionStartTime"):
                    receptions = receptionRepository.filterAllByStartTime(req.getParameter("receptionFilterInput"));
                    req.setAttribute("receptions", receptions);
                    break;
                case ("filterReceptionEndTime"):
                    receptions = receptionRepository.filterAllByEndTime(req.getParameter("receptionFilterInput"));
                    req.setAttribute("receptions", receptions);
                    break;
                case ("filterReceptionDoctorSecondName"):
                    receptions = receptionRepository.filterAllByDoctorSecondName(req.getParameter("receptionFilterInput"));
                    req.setAttribute("receptions", receptions);
                    break;
                case ("filterReceptionPatientSecondName"):
                    receptions = receptionRepository.filterAllByPatientSecondName(req.getParameter("receptionFilterInput"));
                    req.setAttribute("receptions", receptions);
                    break;
                case ("filterReceptionDoctorId"):
                    receptions = receptionRepository.filterAllByDoctorId(Long.valueOf(req.getParameter("receptionFilterInput")));
                    req.setAttribute("receptions", receptions);
                    break;
                case ("filterReceptionPatientId"):
                    receptions = receptionRepository.filterAllByPatientId(Long.valueOf(req.getParameter("receptionFilterInput")));
                    req.setAttribute("receptions", receptions);
                    break;
                default:
                    receptions = receptionRepository.findAll();
                    req.setAttribute("receptions", receptions);
                    break;
            }
        } else {
            receptions = receptionRepository.findAll();
            req.setAttribute("receptions", receptions);
        }

        getServletContext().getRequestDispatcher("/jsp/main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String insertDoctorFirstName = req.getParameter("insertDoctorFirstName");
        String insertDoctorSecondName = req.getParameter("insertDoctorSecondName");
        String insertDoctorSpeciality = req.getParameter("insertDoctorSpeciality");

        String insertPatientFirstName = req.getParameter("insertPatientFirstName");
        String insertPatientSecondName = req.getParameter("insertPatientSecondName");
        String insertPatientPhoneNumber = req.getParameter("insertPatientPhoneNumber");

        String insertReceptionDate = req.getParameter("insertReceptionDate");
        String insertReceptionStartTime = req.getParameter("insertReceptionStartTime");
        String insertReceptionEndTime = req.getParameter("insertReceptionEndTime");
        String insertReceptionDoctorId = req.getParameter("insertReceptionDoctorId");
        String insertReceptionPatientId = req.getParameter("insertReceptionPatientId");

        String updateDoctorId = req.getParameter("updateDoctorId");
        String updateDoctorFirstName = req.getParameter("updateDoctorFirstName");
        String updateDoctorSecondName = req.getParameter("updateDoctorSecondName");
        String updateDoctorSpeciality = req.getParameter("updateDoctorSpeciality");

        String updatePatientId = req.getParameter("updatePatientId");
        String updatePatientFirstName = req.getParameter("updatePatientFirstName");
        String updatePatientSecondName = req.getParameter("updatePatientSecondName");
        String updatePatientPhoneNumber = req.getParameter("updatePatientPhoneNumber");

        String updateReceptionId = req.getParameter("updateReceptionId");
        String updateReceptionDate = req.getParameter("updateReceptionDate");
        String updateReceptionStartTime = req.getParameter("updateReceptionStartTime");
        String updateReceptionEndTime = req.getParameter("updateReceptionEndTime");
        String updateReceptionDoctorId = req.getParameter("updateReceptionDoctorId");
        String updateReceptionPatientId = req.getParameter("updateReceptionPatientId");

        String[] deleteDoctors = req.getParameterValues("doctorCheckbox");
        String[] deletePatients = req.getParameterValues("patientCheckbox");
        String[] deleteReceptions = req.getParameterValues("receptionCheckbox");

        if (nonNull(insertDoctorFirstName) && nonNull(insertDoctorSecondName) && nonNull(insertDoctorSpeciality)) {
            doctorRepository.insert(Doctor.builder()
                    .firstName(insertDoctorFirstName)
                    .secondName(insertDoctorSecondName)
                    .speciality(insertDoctorSpeciality)
                    .build());
            System.out.println(insertDoctorFirstName);
        }

        if (nonNull(insertPatientFirstName) && nonNull(insertPatientSecondName) && nonNull(insertPatientPhoneNumber)) {
            patientRepository.insert(Patient.builder()
                    .firstName(insertPatientFirstName)
                    .secondName(insertPatientSecondName)
                    .phoneNumber(insertPatientPhoneNumber)
                    .build());
        }

        if (nonNull(insertReceptionDate) && nonNull(insertReceptionStartTime) && nonNull(insertReceptionEndTime) && nonNull(insertReceptionDoctorId) && nonNull(insertReceptionPatientId)) {
            receptionRepository.insert(Reception.builder()
                    .date(insertReceptionDate)
                    .startTime(insertReceptionStartTime)
                    .endTime(insertReceptionEndTime)
                    .doctorId(Long.valueOf(insertReceptionDoctorId))
                    .patientId(Long.valueOf(insertReceptionPatientId))
                    .build());
        }

        if (nonNull(updateDoctorId)) {
            doctorRepository.update(Doctor.builder()
                    .id(Long.valueOf(updateDoctorId))
                    .firstName(updateDoctorFirstName)
                    .secondName(updateDoctorSecondName)
                    .speciality(updateDoctorSpeciality)
                    .build());
        }

        if (nonNull(updatePatientId)) {
            patientRepository.update(Patient.builder()
                    .id(Long.valueOf(updatePatientId))
                    .firstName(updatePatientFirstName)
                    .secondName(updatePatientSecondName)
                    .phoneNumber(updatePatientPhoneNumber)
                    .build());
        }

        if (nonNull(updateReceptionId)) {
            receptionRepository.update(Reception.builder()
                    .id(Long.valueOf(updateReceptionId))
                    .date(updateReceptionDate)
                    .startTime(updateReceptionStartTime)
                    .endTime(updateReceptionEndTime)
                    .doctorId(Long.valueOf(updateReceptionDoctorId))
                    .patientId(Long.valueOf(updateReceptionPatientId))
                    .build());
        }

        if (nonNull(deleteDoctors)) {
            for (String id : deleteDoctors) {
                for (Reception reception : receptionRepository.findAll()) {
                    if (reception.getDoctorId().equals(Long.valueOf(id))) {
                        receptionRepository.delete(reception.getId());
                    }
                }
                doctorRepository.delete(Long.valueOf(id));
            }
        }

        if (nonNull(deletePatients)) {
            for (String id : deletePatients) {
                for (Reception reception : receptionRepository.findAll()) {
                    if (reception.getPatientId().equals(Long.valueOf(id))) {
                        receptionRepository.delete(reception.getId());
                    }
                }
                patientRepository.delete(Long.valueOf(id));
            }
        }

        if (nonNull(deleteReceptions)) {
            for (String id : deleteReceptions) {
                receptionRepository.delete(Long.valueOf(id));
            }
        }

        resp.sendRedirect("/main");
    }
}
