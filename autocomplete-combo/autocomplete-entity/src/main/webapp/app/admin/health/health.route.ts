import { Route } from '@angular/router';

import { AueHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
    path: 'aue-health',
    component: AueHealthCheckComponent,
    data: {
        pageTitle: 'health.title'
    }
};
