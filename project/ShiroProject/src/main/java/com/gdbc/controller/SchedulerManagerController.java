package com.gdbc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdbc.mybatis.TiggerGroup;
import com.gdbc.mybatis.TriggerModel;

@Controller
@RequestMapping("/scheduler")
public class SchedulerManagerController {

	@Resource
	private Scheduler scheduler;
	
	@RequestMapping({ "/list", "" })
	public String dashboard(String hasError, Model model) {
		getBaseMv(model);
		
		return "/schedulerManager";
	}
	
	@RequestMapping(value = "/run", method = RequestMethod.GET)
	public String run(Model model) {
		try {
			 scheduler.start();
			String[] triggerGroups = scheduler.getTriggerGroupNames();
			for (int i = 0; i < triggerGroups.length; i++) {
				String[] triggersInGroup = scheduler.getTriggerNames(triggerGroups[i]);
				for (int j = 0; j < triggersInGroup.length; j++) {
					scheduler.resumeTrigger(triggersInGroup[j], triggerGroups[i]);
					scheduler.pauseTrigger(triggersInGroup[j], triggerGroups[i]);
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "redirect:/scheduler/list";
		
	}
	@RequestMapping(value = "/stop", method = RequestMethod.GET)
	public String stop() {
		try {
			scheduler.standby();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "redirect:/scheduler/list";
	}
	
	
	private void getBaseMv(Model model) {
		model.addAttribute("scheduler", scheduler);
		String[] triggerGroups = null;

		String[] triggersInGroup = null;
		try {
			if (scheduler.isInStandbyMode()) {
				return;
			}
			triggerGroups = scheduler.getTriggerGroupNames();
			List<TiggerGroup> tiggerGroups = new ArrayList<TiggerGroup>();
			for (int i = 0; i < triggerGroups.length; i++) {
				TiggerGroup tiggerGroup = new TiggerGroup();
				tiggerGroup.setGroupName(triggerGroups[i]);

				triggersInGroup = scheduler.getTriggerNames(triggerGroups[i]);
				List<TriggerModel> triggerModels = new ArrayList<TriggerModel>();
				for (int j = 0; j < triggersInGroup.length; j++) {
					Trigger trigger = scheduler.getTrigger(triggersInGroup[j], triggerGroups[i]);
					if (trigger instanceof CronTrigger) {
						TriggerModel tmodel = new TriggerModel();
						String des = trigger.getDescription();
						trigger.setDescription(des);
						tmodel.setTrigger(trigger);
						tmodel.setStatus(scheduler.getTriggerState(triggersInGroup[j], triggerGroups[i]));
						triggerModels.add(tmodel);
					}
				}
				tiggerGroup.setTriggerModels(triggerModels);
				tiggerGroups.add(tiggerGroup);
			}
			model.addAttribute("tiggerGroups", tiggerGroups);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 启动
	 */
	@RequestMapping(value = "/resumeTrigger", method = RequestMethod.GET)
	public String resumeTrigger(String name, String group) {
		try {
			scheduler.resumeTrigger(name, group);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/scheduler/list";
	}

	/**
	 * 停止
	 */
	@RequestMapping(value = "/pauseTrigger", method = RequestMethod.GET)
	public String pauseTrigger(String name, String group) {
		try {
			scheduler.pauseTrigger(name, group);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/scheduler/list";
	}
	

	/**
	 * 立即启动
	 */
	@RequestMapping(value = "/triggerTrigger", method = RequestMethod.GET)
	public String triggerTrigger(String name, String group, Model model) {
		try {
			Trigger trigger = scheduler.getTrigger(name, group);
			scheduler.triggerJob(trigger.getJobName(), trigger.getJobGroup());
		} catch (Exception e) {
			model.addAttribute("hasError", "true");
			e.printStackTrace();
		}
		return "redirect:/scheduler/list";
	}
}
