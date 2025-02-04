import React, { Component } from 'react';
import ProjectTask from './ProjectTasks/ProjectTask';

class Backlog extends Component {
    render() {
        const { project_tasks } = this.props;

        const tasks = project_tasks.map(project_task => (
            <ProjectTask key={project_task.id} project_task={project_task} />
        ));

        let todoItems = [];
        let inProgessItems = [];
        let doneItems = [];

        for (let i = 0; i < tasks.length; i++) {
            let currentTask = tasks[i];
            switch (currentTask.props.project_task.status) {
                case "TO_DO":
                    todoItems.push(currentTask);
                    break;

                case "IN_PROGRESS":
                    inProgessItems.push(currentTask);
                    break;

                case "DONE":
                    doneItems.push(currentTask);
                    break;

                default:
                    break;
            }
        }

        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-4">
                        <div className="card text-center mb-2">
                            <div className="card-header bg-secondary text-white">
                                <h3>TO DO</h3>
                            </div>
                        </div>

                        {todoItems}
                    </div>
                    <div className="col-md-4">
                        <div className="card text-center mb-2">
                            <div className="card-header bg-primary text-white">
                                <h3>In Progress</h3>
                            </div>
                        </div>

                        {inProgessItems}
                    </div>
                    <div className="col-md-4">
                        <div className="card text-center mb-2">
                            <div className="card-header bg-success text-white">
                                <h3>Done</h3>
                            </div>
                        </div>

                        {doneItems}
                    </div>
                </div>
            </div>
        );
    }
}

export default Backlog;
