import { Route } from '@angular/router';

import { AudHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
    path: 'aud-health',
    component: AudHealthCheckComponent,
    data: {
        pageTitle: 'health.title'
    }
};
