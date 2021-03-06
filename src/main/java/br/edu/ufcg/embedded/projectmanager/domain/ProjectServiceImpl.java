package br.edu.ufcg.embedded.projectmanager.domain;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import br.edu.ufcg.embedded.projectmanager.business.Bus;
import br.edu.ufcg.embedded.projectmanager.data.ProjectRepository;
import br.edu.ufcg.embedded.projectmanager.exception.ProjectException;
import br.edu.ufcg.embedded.projectmanager.generic.GenericServiceImpl;
import br.edu.ufcg.embedded.projectmanager.generic.event.CreateRequestEvent;
import br.edu.ufcg.embedded.projectmanager.generic.event.CreateResponseEvent;

@Service
public class ProjectServiceImpl extends
		GenericServiceImpl<Project, ProjectRepository> implements
		ProjectService {

	@Transactional
	@Override
	public CreateResponseEvent<Project> request(
			@Valid CreateRequestEvent<Project> request) {
		
		try {
			Project object = repository.saveAndFlush(request.getObject());
		
			Bus.fireProjectEvent(Bus.PROJECT_CREATED, object);
			
			return new CreateResponseEvent<Project>(object);
		} catch (ProjectException e) {
			e.printStackTrace(); //tratar
			return null;
		}

		
	}

}
