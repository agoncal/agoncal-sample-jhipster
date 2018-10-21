import { Route } from '@angular/router';

import { NoaHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
    path: 'noa-health',
    component: NoaHealthCheckComponent,
    data: {
        pageTitle: 'health.title'
    }
};
