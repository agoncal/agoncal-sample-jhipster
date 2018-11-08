import { Route } from '@angular/router';

import { JacHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
    path: 'jac-health',
    component: JacHealthCheckComponent,
    data: {
        pageTitle: 'health.title'
    }
};
