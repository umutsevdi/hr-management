package com.hr.management.ui.client;

import com.hr.management.api.service.EmployeeService;
import com.hr.management.api.service.EmployeeStatusService;
import com.hr.management.api.service.TeamService;
import com.hr.management.api.service.model.EmployeeDto;
import com.hr.management.api.service.model.EmployeeStatusDto;
import com.hr.management.api.service.model.EmployeeStatusPastDto;
import com.hr.management.api.service.model.TeamDto;
import com.hr.management.ui.client.view.EmployeeView;
import com.hr.management.ui.client.view.TeamView;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Getter
public class PageClient {
    private final EmployeeService employeeService;
    private final EmployeeStatusService employeeStatusService;
    private final TeamService teamService;

    public PageClient(EmployeeService employeeService, EmployeeStatusService employeeStatusService,
                      TeamService teamService) {
        this.employeeService = employeeService;
        this.employeeStatusService = employeeStatusService;
        this.teamService = teamService;
    }

    public List<TeamDto> getTeams() {
        return teamService.findAll();
    }

    public List<EmployeeView> toView(Collection<EmployeeDto> e) {
        Map<Long, TeamDto> teamMap = getTeams().stream().collect(Collectors.toMap(TeamDto::getId, Function.identity()));

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
            } else {
                employeeViewCollection.add(new EmployeeView(i, null));
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


    public TeamView toView(TeamDto e) {
        if (e == null) {
            return null;
        }
        EmployeeDto boss;
        try {
            boss = employeeService.findEmployeeById(e.getBossId());
        } catch (Exception exception) {
            boss = null;
            exception.printStackTrace();
        }
        List<EmployeeDto> employeeDtoList = employeeService.findByTeamId(e.getId());
        return new TeamView(e, boss, employeeDtoList);
    }

    public List<TeamView> toView(List<TeamDto> e) {
        if (CollectionUtils.isEmpty(e)) {
            return Collections.emptyList();
        }
        return e.stream().map(this::toView).collect(Collectors.toList());
    }

    public List<EmployeeView> filterBy(List<EmployeeView> baseList, String query) {
        Map<String, String> filter = parseQuery(query);
        if (filter.size() == 0) {
            return baseList;
        }
        List<EmployeeView> finalList = new ArrayList<>(baseList);
        for (Map.Entry<String, String> entry : filter.entrySet()) {
            switch (entry.getKey()) {
                case "id":
                    finalList.removeIf(i -> !i.getId().toString().equals(entry.getValue()));
                    break;
                case "firstName":
                    finalList.removeIf(i -> !i.getFirstName().toLowerCase().contains(entry.getValue()));
                    break;
                case "lastName":
                    finalList.removeIf(i -> !i.getLastName().toLowerCase().contains(entry.getValue()));
                    break;
                case "email":
                    finalList.removeIf(i -> !i.getEmail().toLowerCase().contains(entry.getValue()));
                    break;
                case "phone":
                    finalList.removeIf(i -> !i.getPhoneNumber().contains(entry.getValue()));
                    break;
                case "education":
                    finalList.removeIf(i -> !i.getEducation().toLowerCase().contains(entry.getValue()));
                    break;
                case "experience":
                    finalList.removeIf(i -> !i.getExperience().toString().equals(entry.getValue()));
                    break;
                case "team":
                    finalList.removeIf(i -> !i.getTeam().getName().toLowerCase().contains(entry.getValue()));
                    break;
                case "title":
                    finalList.removeIf(i -> !i.getTitle().toLowerCase().contains(entry.getValue()));
                    break;
            }

        }
        return finalList;
    }

    private Map<String, String> parseQuery(String query) {
        String[] entries = query.split(",");
        Map<String, String> parsedQuery = new HashMap<>(entries.length);
        for (String entry : entries) {
            String[] statement = entry.split("=");
            if (statement.length == 2) {
                statement[0] = statement[0].strip();
                statement[1] = statement[1].strip().toLowerCase();
                parsedQuery.put(statement[0], statement[1]);
            }
        }
        return parsedQuery;
    }

    public void save(EmployeeView employeeView) {


    }

}
