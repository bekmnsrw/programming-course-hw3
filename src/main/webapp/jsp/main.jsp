<%@ page import="com.example.hw3.models.Doctor" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.hw3.models.Patient" %>
<%@ page import="com.example.hw3.models.Reception" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
    <link href="../css/table.css" rel="stylesheet" type="text/css">
</head>
<body>
    <form method="post">
        <label>Update a doctor</label>
        <input type="text" name="updateDoctorId" placeholder="ID">
        <input type="text" name="updateDoctorFirstName" placeholder="First name">
        <input type="text" name="updateDoctorSecondName" placeholder="Second name">
        <input type="text" name="updateDoctorSpeciality" placeholder="Speciality">
        <button type="submit">Update</button>
    </form>

    <form method="post">
        <label>Update a patient</label>
        <input type="text" name="updatePatientId" placeholder="ID">
        <input type="text" name="updatePatientFirstName" placeholder="First name">
        <input type="text" name="updatePatientSecondName" placeholder="Second name">
        <input type="text" name="updatePatientPhoneNumber" placeholder="Phone (+___________)">
        <button type="submit">Update</button>
    </form>

    <form method="post">
        <label>Update a reception</label>
        <input type="text" name="updateReceptionId" placeholder="ID">
        <input type="text" name="updateReceptionDate" placeholder="Date (yyyy-MM-dd)">
        <input type="text" name="updateReceptionStartTime" placeholder="Start time (HH:mm)">
        <input type="text" name="updateReceptionEndTime" placeholder="End time (HH:mm)">
        <input type="text" name="updateReceptionDoctorId" placeholder="Doctor's ID">
        <input type="text" name="updateReceptionPatientId" placeholder="Patient's ID">
        <button type="submit">Update</button>
    </form>

    <br>

    <form method="post">
        <label>Insert a doctor</label>
        <input type="text" name="insertDoctorFirstName" placeholder="First name">
        <input type="text" name="insertDoctorSecondName" placeholder="Second name">
        <input type="text" name="insertDoctorSpeciality" placeholder="Speciality">
        <button type="submit">Insert</button>
    </form>

    <form method="post">
        <label>Insert a patient</label>
        <input type="text" name="insertPatientFirstName" placeholder="First name">
        <input type="text" name="insertPatientSecondName" placeholder="Second name">
        <input type="text" name="insertPatientPhoneNumber" placeholder="Phone (+___________)">
        <button type="submit">Insert</button>
    </form>

    <form method="post">
        <label>Insert a reception</label>
        <input type="text" name="insertReceptionDate" placeholder="Date (yyyy-MM-dd)">
        <input type="text" name="insertReceptionStartTime" placeholder="Start time (HH:mm)">
        <input type="text" name="insertReceptionEndTime" placeholder="End time (HH:mm)">
        <input type="text" name="insertReceptionDoctorId" placeholder="Doctor's ID">
        <input type="text" name="insertReceptionPatientId" placeholder="Patient's ID">
        <button type="submit">Insert</button>
    </form>

    <% List<Doctor> doctors = (List<Doctor>) request.getAttribute("doctors"); %>

    <br>
    <br>

    <form method="post">
        <table>
            <caption style="font-size: 10px"><h1>Doctors</h1></caption>
            <tr>
                <th>ID</th>
                <th>First name</th>
                <th>Second name</th>
                <th>Speciality</th>
                <th>Delete</th>
            </tr>
            <% for (Doctor doctor : doctors) { %>
                <tr>
                    <td><%=doctor.getId()%></td>
                    <td><%=doctor.getFirstName()%></td>
                    <td><%=doctor.getSecondName()%></td>
                    <td><%=doctor.getSpeciality()%></td>
                    <td><input type ="checkbox" name="doctorCheckbox" value="<%=Long.valueOf(doctor.getId())%>"></td>
                </tr>
            <%}%>
        </table>
        <input type="submit" value="Delete selected">
    </form>

    <form method="get">
        <label> Filter doctors by
            <select name="doctorFilter">
                <option value="filterDoctorNone">None</option>
                <option value="filterDoctorFirstName">First name</option>
                <option value="filterDoctorSecondName">Second name</option>
                <option value="filterDoctorSpeciality">Speciality</option>
            </select>
        </label>
        <input type="text" name="doctorFilterInput">
        <input type="submit" value="Show">
    </form>

    <% List<Patient> patients = (List<Patient>) request.getAttribute("patients"); %>

    <br>
    <br>

    <form method="post">
        <table>
            <caption style="font-size: 10px"><h1>Patients</h1></caption>
            <tr>
                <th>ID</th>
                <th>First name</th>
                <th>Second name</th>
                <th>Phone number</th>
                <th>Delete</th>
            </tr>
            <% for (Patient patient : patients) { %>
                <tr>
                    <td><%=patient.getId()%></td>
                    <td><%=patient.getFirstName()%></td>
                    <td><%=patient.getSecondName()%></td>
                    <td><%=patient.getPhoneNumber()%></td>
                    <td><input type ="checkbox" name="patientCheckbox" value="<%=Long.valueOf(patient.getId())%>"></td>
                </tr>
            <%}%>
        </table>
        <input type="submit" value="Delete selected">
    </form>

    <form method="get">
        <label> Filter patients by
            <select name="patientFilter">
                <option value="filterPatientNone">None</option>
                <option value="filterPatientFirstName">First name</option>
                <option value="filterPatientSecondName">Second name</option>
                <option value="filterPatientPhoneNumber">Phone number</option>
            </select>
        </label>
        <input type="text" name="patientFilterInput">
        <input type="submit" value="Show">
    </form>

    <% List<Reception> receptions = (List<Reception>) request.getAttribute("receptions"); %>

    <br>
    <br>

    <form method="post">
        <table>
            <caption style="font-size: 10px"><h1>Receptions</h1></caption>
            <tr>
                <th>ID</th>
                <th>Date</th>
                <th>Start time</th>
                <th>End time</th>
                <th>Doctor second name</th>
                <th>Patient second name</th>
                <th>Doctor ID</th>
                <th>Patient ID</th>
                <th>Delete</th>
            </tr>
            <% for (Reception reception : receptions) { %>
                <tr>
                    <td><%=reception.getId()%></td>
                    <td><%=reception.getDate()%></td>
                    <td><%=reception.getStartTime()%></td>
                    <td><%=reception.getEndTime()%></td>
                    <td><%=reception.getDoctorSecondName()%></td>
                    <td><%=reception.getPatientSecondName()%></td>
                    <td><%=reception.getDoctorId()%></td>
                    <td><%=reception.getPatientId()%></td>
                    <td><input type ="checkbox" name="receptionCheckbox" value="<%=Long.valueOf(reception.getId())%>"></td>
                </tr>
            <%}%>
        </table>
        <input type="submit" value="Delete selected">
    </form>

    <form method="get">
        <label> Filter receptions by
            <select name="receptionFilter">
                <option value="filterReceptionNone">None</option>
                <option value="filterReceptionDate">Date</option>
                <option value="filterReceptionStartTime">Start time</option>
                <option value="filterReceptionEndTime">End time</option>
                <option value="filterReceptionDoctorSecondName">Doctor second name </option>
                <option value="filterReceptionPatientSecondName">Patient second name</option>
                <option value="filterReceptionDoctorId">Doctor ID</option>
                <option value="filterReceptionPatientId">Patient ID</option>
            </select>
        </label>
        <input type="text" name="receptionFilterInput">
        <input type="submit" value="Show">
    </form>

</body>
</html>
