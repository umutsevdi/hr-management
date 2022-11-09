package com.hr.management.ui.client;

import com.hr.management.api.service.EmployeeService;
import com.hr.management.api.service.EmployeeStatusService;
import com.hr.management.api.service.TeamService;
import com.hr.management.api.service.model.EmployeeDto;
import com.hr.management.api.service.model.EmployeeStatusDto;
import com.hr.management.api.service.model.TeamDto;
import com.hr.management.ui.client.view.EmployeeView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Getter
public class EmployeeClient {
    private EmployeeService employeeService;
    private EmployeeStatusService employeeStatusService;
    private TeamService teamService;

    public List<EmployeeView> getEmployeesForView() {
        List<EmployeeDto> employeeList = employeeService.findAll();
        Map<Long, TeamDto> teamMap = teamService.findAll().stream()
                .collect(Collectors.toMap(TeamDto::getId, Function.identity()));

        List<EmployeeStatusDto> employeeStatusList = employeeStatusService.findAll();
        Map<Long, List<EmployeeStatusDto>> idEmployeeStatusMap = new HashMap<>(employeeStatusList.size());

        for (EmployeeStatusDto e : employeeStatusList) {
            if (idEmployeeStatusMap.containsKey(e.getEmployeeId())) {
                idEmployeeStatusMap.get(e.getEmployeeId()).add(e);
            } else {
                idEmployeeStatusMap.put(e.getEmployeeId(), new LinkedList<>(Collections.singletonList(e)));
            }
        }

        List<EmployeeView> employeeViewList = new LinkedList<>();
        for (EmployeeDto i : employeeList) {
            List<EmployeeStatusDto> e = idEmployeeStatusMap.get(i.getId());
            if (!CollectionUtils.isEmpty(e))
                employeeViewList.add(new EmployeeView(i, teamMap.get(e.get(0).getTeamId()), e.get(0)));

        }
        return employeeViewList;
    }

    public List<EmployeeView> filterBy(List<EmployeeView> employeeViewList, Map<String, String> filter) {
        List<EmployeeView> baseList = new ArrayList<>(employeeViewList);
        for (Map.Entry<String, String> entry : filter.entrySet()) {
            switch (entry.getKey()) {
                case "id": {
                    baseList.removeIf(i -> !i.getId().toString().equals(entry.getValue()));
                    break;
                }
                case "firstName": {
                    baseList.removeIf(i -> !i.getFirstName().contains(entry.getValue()));
                    break;
                }
                case "lastName": {
                    baseList.removeIf(i -> !i.getLastName().contains(entry.getValue()));
                    break;
                }
                case "email": {
                    baseList.removeIf(i -> !i.getEmail().contains(entry.getValue()));
                    break;
                }
                case "phone": {
                    baseList.removeIf(i -> !i.getPhoneNumber().contains(entry.getValue()));
                    break;
                }
                case "education": {
                    baseList.removeIf(i -> !i.getEducation().contains(entry.getValue()));
                    break;
                }
                case "experience": {
                    baseList.removeIf(i -> !i.getExperience().toString().equals(entry.getValue()));
                    break;
                }
                case "team": {
                    baseList.removeIf(i -> !i.getTeam().getName().contains(entry.getValue()));
                    break;
                }
                case "title": {
                    baseList.removeIf(i -> !i.getTitle().contains(entry.getValue()));
                    break;
                }
            }

        }
        return baseList;

    }

}
