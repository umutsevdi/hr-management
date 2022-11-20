package com.hr.management.ui.client;

import com.hr.management.api.service.EmployeeService;
import com.hr.management.api.service.EmployeeStatusService;
import com.hr.management.api.service.TeamService;
import com.hr.management.api.service.model.EmployeeDto;
import com.hr.management.api.service.model.EmployeeStatusDto;
import com.hr.management.api.service.model.EmployeeStatusPastDto;
import com.hr.management.api.service.model.TeamDto;
import com.hr.management.ui.client.view.EmployeeView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Getter
public class PageClient {

    private EmployeeService employeeService;
    private EmployeeStatusService employeeStatusService;
    private TeamService teamService;

    public List<EmployeeView> toView(Collection<EmployeeDto> e) {
        Map<Long, TeamDto> teamMap = teamService.findAll().stream().collect(Collectors.toMap(TeamDto::getId, Function.identity()));

        Map<Long, EmployeeStatusDto> employeeStatusMap = new HashMap<>(e.size());
        employeeStatusService.findAll().forEach(i -> employeeStatusMap.putIfAbsent(i.getEmployeeId(), i));

        Map<Long, List<EmployeeStatusPastDto>> employeeStatusPastMap = e.stream()
                .collect(Collectors.toMap(EmployeeDto::getId, i -> new LinkedList<>()));

        employeeStatusService.findEmployeeStatusPastsByEmployeeIdIn(e.stream()
                        .map(EmployeeDto::getId)
                        .collect(Collectors.toList()))
                .forEach(i -> employeeStatusPastMap.get(i.getEmployeeId()).add(i));

        List<EmployeeView> employeeViewCollection = new LinkedList<>();
        for (EmployeeDto i : e) {
            if (employeeStatusMap.containsKey(i.getId())) {
                employeeViewCollection.add(
                        new EmployeeView(
                                i,
                                teamMap.get(employeeStatusMap.get(i.getId()).getTeamId()),
                                employeeStatusPastMap.get(i.getId()),
                                employeeStatusMap.get(i.getId()))
                );
            }
        }
        return employeeViewCollection;
    }

    public EmployeeView toView(EmployeeDto e) {
        if (e == null) {
            return null;
        }
        EmployeeStatusDto employeeStatus = employeeStatusService.findEmployeeStatusesByEmployeeId(e.getId());
        TeamDto team = teamService.findById(employeeStatus.getTeamId());
        List<EmployeeStatusPastDto> employeeStatusPast = employeeStatusService.findEmployeeStatusPastsByEmployeeId(e.getId());
        return new EmployeeView(e, team, employeeStatusPast, employeeStatus);
    }

    public List<EmployeeView> filterBy(List<EmployeeView> employeeViewList, String query) {
        Map<String, String> filter = parseQuery(query);
        if (filter.size() == 0) {
            return employeeViewList;
        }
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

    private Map<String, String> parseQuery(String query) {
        Map<String, String> parsedQuery = new HashMap<>();
        for (String entry : query.split(",")) {
            String[] singleQuery = entry.split(":");
            if (singleQuery.length == 2) {
                singleQuery[0] = singleQuery[0].strip();
                singleQuery[1] = singleQuery[1].strip();
                parsedQuery.put(singleQuery[0], singleQuery[1]);
            }
        }
        return parsedQuery;
    }

}
